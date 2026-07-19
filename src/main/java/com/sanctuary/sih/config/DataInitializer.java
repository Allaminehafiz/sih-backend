package com.sanctuary.sih.config;

import com.sanctuary.sih.medical.entity.CreneauTravail;
import com.sanctuary.sih.medical.repository.CreneauTravailRepository;
import com.sanctuary.sih.staff.entity.Utilisateur;
import com.sanctuary.sih.staff.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CreneauTravailRepository creneauRepo;
    private final UtilisateurRepository utilisateurRepo;

    @Override
    public void run(String... args) {
        if (creneauRepo.count() == 0) {
            List<Utilisateur> medecins = utilisateurRepo.findByRole(Utilisateur.Role.MEDECIN);
            for (Utilisateur medecin : medecins) {
                // Du lundi (1) au vendredi (5), 8h-17h
                for (int jour = 1; jour <= 5; jour++) {
                    CreneauTravail ct = new CreneauTravail();
                    ct.setMedecinId(medecin.getId());
                    ct.setJourSemaine(jour);
                    ct.setHeureDebut(LocalTime.of(8, 0));
                    ct.setHeureFin(LocalTime.of(17, 0));
                    creneauRepo.save(ct);
                }
            }
        }
    }
}