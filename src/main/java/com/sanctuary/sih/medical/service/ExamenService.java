package com.sanctuary.sih.medical.service;

import com.sanctuary.sih.medical.entity.Examen;
import com.sanctuary.sih.medical.repository.ExamenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamenService {

    private final ExamenRepository examenRepository;

    public Examen createExamen(Examen examen) {
        return examenRepository.save(examen);
    }

    public List<Examen> getExamensByPatient(Long patientId) {
        return examenRepository.findByPatientId(patientId);
    }

    public Examen updateExamen(Long id, Examen details) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examen non trouvé"));
        examen.setResultat(details.getResultat());
        examen.setStatut(details.getStatut());
        return examenRepository.save(examen);
    }
}