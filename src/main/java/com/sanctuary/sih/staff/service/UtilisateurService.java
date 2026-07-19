package com.sanctuary.sih.staff.service;

import com.sanctuary.sih.config.EmailService;
import com.sanctuary.sih.staff.entity.Utilisateur;
import com.sanctuary.sih.staff.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        // Générer un token de réinitialisation
        String token = UUID.randomUUID().toString();
        utilisateur.setResetToken(token);
        utilisateur.setResetTokenExpiration(LocalDateTime.now().plusDays(7)); // valable 7 jours
        Utilisateur saved = utilisateurRepository.save(utilisateur);

        String lien = "http://localhost:5173/definir-mot-de-passe?token=" + token;
        emailService.envoyerEmail(
                saved.getEmail(),
                "Sanctuary Health - Création de votre compte",
                "Bonjour " + saved.getNom() + ",\n\n" +
                        "Votre compte a été créé sur Sanctuary Health.\n" +
                        "Pour définir votre mot de passe, cliquez sur le lien suivant :\n" +
                        lien + "\n\n" +
                        "Ce lien expire dans 7 jours.\n\n" +
                        "Cordialement,\nL'équipe Sanctuary Health");
        return saved;
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public List<Utilisateur> getByRole(Utilisateur.Role role) {
        return utilisateurRepository.findByRole(role);
    }

    public Utilisateur updateUtilisateur(Long id, Utilisateur details) {
        Utilisateur user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setNom(details.getNom());
        user.setEmail(details.getEmail());
        user.setRole(details.getRole());
        user.setTelephone(details.getTelephone());
        user.setDepartement(details.getDepartement());
        user.setSpecialite(details.getSpecialite());
        // Ne pas modifier le mot de passe ici
        return utilisateurRepository.save(user);
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

}