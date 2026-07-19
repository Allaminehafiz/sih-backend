package com.sanctuary.sih.config;

import com.sanctuary.sih.staff.entity.Utilisateur;
import com.sanctuary.sih.staff.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (utilisateurRepository.count() == 0) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Administrateur Système");
            admin.setEmail("admin1@sanctuary.med");
            admin.setMotDePasse(passwordEncoder.encode("changeme123"));
            admin.setRole(Utilisateur.Role.ADMIN);
            admin.setActif(true);
            utilisateurRepository.save(admin);

            System.out.println("============================================");
            System.out.println(" COMPTE ADMIN CRÉÉ ");
            System.out.println(" Email : admin@sanctuary.med");
            System.out.println(" Mot de passe : changeme123");
            System.out.println("============================================");
        }
    }
}