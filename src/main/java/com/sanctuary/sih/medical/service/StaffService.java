package com.sanctuary.sih.medical.service;

import com.sanctuary.sih.medical.entity.CreneauTravail;
import com.sanctuary.sih.medical.repository.CreneauTravailRepository;
import com.sanctuary.sih.staff.entity.Utilisateur;
import com.sanctuary.sih.staff.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StaffService {
    private final UtilisateurRepository utilisateurRepository;
    private final CreneauTravailRepository creneauTravailRepository;
    public StaffService(UtilisateurRepository utilisateurRepository, CreneauTravailRepository creneauTravailRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.creneauTravailRepository = creneauTravailRepository;
    }

    public Utilisateur createMedecin(Utilisateur medecin, List<CreneauTravail> creneaux) {

        Utilisateur saved = utilisateurRepository.save(medecin);
        for (CreneauTravail ct : creneaux) {
            ct.setMedecinId(saved.getId());
            creneauTravailRepository.save(ct);
        }
        return saved;
    }
}
