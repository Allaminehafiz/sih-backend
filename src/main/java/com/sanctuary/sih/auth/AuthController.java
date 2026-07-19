package com.sanctuary.sih.auth;

import com.sanctuary.sih.config.EmailService;
import com.sanctuary.sih.config.JwtUtil;
import com.sanctuary.sih.staff.entity.Utilisateur;
import com.sanctuary.sih.staff.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (utilisateurRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error", "Cet email est déjà utilisé"));
        }

        Utilisateur utilisateur = new Utilisateur();
        // Concaténer prénom et nom dans le champ "nom"
        utilisateur.setNom((request.prenom() + " " + request.nom()).trim());
        utilisateur.setEmail(request.email());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.password()));
        utilisateur.setTelephone(request.telephone());
        utilisateur.setDepartement(request.departement());
        utilisateur.setSpecialite(request.specialite());
        utilisateur.setRole(Utilisateur.Role.valueOf(request.role()));
        utilisateur.setActif(true);

        utilisateurRepository.save(utilisateur);

        return ResponseEntity.ok(Map.of("message", "Compte créé avec succès"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(request.email());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
        }

        Utilisateur user = userOpt.get();

        if (!passwordEncoder.matches(request.password(), user.getMotDePasse())) {
            return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "name", user.getNom(),
                "role", user.getRole().name(),
                "email", user.getEmail()
        ));
    }

    public record RegisterRequest(
            String prenom,
            String nom,
            String email,
            String password,
            String telephone,
            String departement,
            String specialite,
            String role
    ) {}

    public record LoginRequest(String email, String password) {}

    @PutMapping("/changer-mot-de-passe")
    public ResponseEntity<?> changerMotDePasse(@RequestBody ChangePasswordRequest request,
                                               @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.ancienMotDePasse(), user.getMotDePasse())) {
            return ResponseEntity.status(403).body(Map.of("error", "Mot de passe actuel incorrect."));
        }

        user.setMotDePasse(passwordEncoder.encode(request.nouveauMotDePasse()));
        utilisateurRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Mot de passe modifié avec succès."));
    }

    public record ChangePasswordRequest(String ancienMotDePasse, String nouveauMotDePasse) {}

    @PostMapping("/definir-mot-de-passe")
    public ResponseEntity<?> definirMotDePasse(@RequestBody DefinirMotDePasseRequest request) {
        Optional<Utilisateur> userOpt = utilisateurRepository.findByResetToken(request.token());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("error", "Token invalide ou expiré."));
        }
        Utilisateur user = userOpt.get();
        if (user.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(400).body(Map.of("error", "Token expiré."));
        }
        // Mettre à jour le mot de passe
        user.setMotDePasse(passwordEncoder.encode(request.nouveauMotDePasse()));
        // Effacer le token pour qu'il ne soit plus utilisable
        user.setResetToken(null);
        user.setResetTokenExpiration(null);
        utilisateurRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Mot de passe défini avec succès."));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("Forgot password demandé pour : " + email);

        Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);
        System.out.println("Utilisateur trouvé : " + userOpt.isPresent());
        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setResetTokenExpiration(LocalDateTime.now().plusHours(24));
            utilisateurRepository.save(user);

            String lien = "http://localhost:5173/definir-mot-de-passe?token=" + token;
            emailService.envoyerEmail(
                    email,
                    "Sanctuary Health - Réinitialisation du mot de passe",
                    "Bonjour " + user.getNom() + ",\n\n" +
                            "Vous avez demandé la réinitialisation de votre mot de passe.\n" +
                            "Cliquez sur le lien suivant : " + lien + "\n\n" +
                            "Ce lien expire dans 24 heures.\n\n" +
                            "Cordialement,\nL'équipe Sanctuary Health"
            );
        }

        // Toujours retourner un succès pour ne pas révéler si l'email existe
        return ResponseEntity.ok(Map.of("message", "Si l'email existe, un lien de réinitialisation a été envoyé."));
    }

    public record DefinirMotDePasseRequest(String token, String nouveauMotDePasse) {}
}