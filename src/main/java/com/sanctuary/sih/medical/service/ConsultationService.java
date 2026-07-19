package com.sanctuary.sih.medical.service;

import com.sanctuary.sih.medical.entity.Consultation;
import com.sanctuary.sih.medical.entity.DossierMedical;
import com.sanctuary.sih.medical.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final DossierMedicalService dossierMedicalService;


    public Consultation createConsultation(Consultation consultation) {
        Consultation saved = consultationRepository.save(consultation);

        // Mise à jour du dossier médical associé
        DossierMedical dossier = dossierMedicalService.getDossierById(consultation.getDossierId());
        if (dossier != null) {
            String nouvelAntecedent = String.format(
                    "[%s] Motif : %s | Médecin : %d | Notes : %s",
                    saved.getDateConsultation().toLocalDate(),
                    saved.getMotif(),
                    saved.getMedecinId(),
                    saved.getNotes() != null ? saved.getNotes() : "Aucune"
            );
            String antecedentsActuels = dossier.getAntecedents();
            dossier.setAntecedents(
                    (antecedentsActuels != null ? antecedentsActuels + "\n" : "") + nouvelAntecedent
            );
            dossierMedicalService.updateDossier(dossier.getId(), dossier);
        }
        return saved;

    }

    public List<Consultation> getConsultationsByDossier(Long dossierId) {
        return consultationRepository.findByDossierIdOrderByDateConsultationDesc(dossierId);
    }

    public List<Consultation> getConsultationsByMedecin(Long medecinId) {
        return consultationRepository.findByMedecinId(medecinId);
    }

    public Consultation updateConsultation(Long id, Consultation details) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée"));
        consultation.setMotif(details.getMotif());
        consultation.setDiagnostic(details.getDiagnostic());
        consultation.setNotes(details.getNotes());
        return consultationRepository.save(consultation);
    }

    public List<Consultation> getConsultationsBetween(LocalDateTime start, LocalDateTime end) {
        return consultationRepository.findByDateConsultationBetween(start, end);
    }
}