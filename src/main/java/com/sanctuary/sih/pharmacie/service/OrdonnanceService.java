package com.sanctuary.sih.pharmacie.service;

import com.sanctuary.sih.pharmacie.entity.Ordonnance;
import com.sanctuary.sih.pharmacie.repository.OrdonnanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdonnanceService {

    private final OrdonnanceRepository ordonnanceRepository;

    public Ordonnance createOrdonnance(Ordonnance ordonnance) {
        return ordonnanceRepository.save(ordonnance);
    }

    public List<Ordonnance> getOrdonnancesByPatient(Long patientId) {
        return ordonnanceRepository.findByPatientId(patientId);
    }

    public Ordonnance validerOrdonnance(Long id) {
        Ordonnance ordonnance = ordonnanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordonnance non trouvée"));
        ordonnance.setStatut(Ordonnance.StatutOrdonnance.SERVIE);
        ordonnance.setDateValidation(java.time.LocalDate.now());
        return ordonnanceRepository.save(ordonnance);
    }
}