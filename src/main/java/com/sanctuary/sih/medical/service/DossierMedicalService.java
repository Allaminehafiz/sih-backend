package com.sanctuary.sih.medical.service;

import com.sanctuary.sih.medical.entity.DossierMedical;
import com.sanctuary.sih.medical.repository.DossierMedicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DossierMedicalService {

    private final DossierMedicalRepository dossierMedicalRepository;

    public DossierMedical createDossier(DossierMedical dossier) {
        return dossierMedicalRepository.save(dossier);
    }

    public List<DossierMedical> getAllDossiers() {
        return dossierMedicalRepository.findAll();
    }

    public Optional<DossierMedical> getDossierByPatientId(Long patientId) {
        return dossierMedicalRepository.findByPatientId(patientId);
    }

    public DossierMedical getDossierById(Long id) {
        return dossierMedicalRepository.findById(id).orElse(null);
    }

    public DossierMedical updateDossier(Long id, DossierMedical details) {
        DossierMedical dossier = dossierMedicalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé"));
        dossier.setGroupeSanguin(details.getGroupeSanguin());
        dossier.setPoids(details.getPoids());
        dossier.setTaille(details.getTaille());
        dossier.setAntecedents(details.getAntecedents());
        dossier.setDiagnostic(details.getDiagnostic());   // ajout
        dossier.setNotes(details.getNotes());
        return dossierMedicalRepository.save(dossier);
    }

    public void deleteDossier(Long id) {
        dossierMedicalRepository.deleteById(id);
    }
}