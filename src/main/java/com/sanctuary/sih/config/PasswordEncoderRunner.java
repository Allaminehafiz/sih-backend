package com.sanctuary.sih.config;

// import com.sanctuary.sih.staff.entity.Utilisateur;
// import com.sanctuary.sih.staff.repository.UtilisateurRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// @Component
// @RequiredArgsConstructor
public class PasswordEncoderRunner /* implements CommandLineRunner */ {

//    private final UtilisateurRepository utilisateurRepository;
//    private final PasswordEncoder passwordEncoder;

//    @Override
//    public void run(String... args) {
//        for (Utilisateur u : utilisateurRepository.findAll()) {
//            // Vérifie si le mot de passe est déjà encodé (BCrypt commence par $2a$)
//            if (u.getMotDePasse() != null && !u.getMotDePasse().startsWith("$2a$")) {
//                u.setMotDePasse(passwordEncoder.encode(u.getMotDePasse()));
//                utilisateurRepository.save(u);
//            }
//        }
//    }
}