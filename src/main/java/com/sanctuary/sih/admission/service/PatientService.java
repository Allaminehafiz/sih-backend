package com.sanctuary.sih.admission.service;

import com.sanctuary.sih.admission.entity.Patient;
import com.sanctuary.sih.admission.repository.PatientRepository;
import com.sanctuary.sih.medical.entity.DossierMedical;
import com.sanctuary.sih.medical.service.DossierMedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final VerificationCouvertureService verificationCouvertureService;
    private final ChambreService chambreService;
    private final DossierMedicalService dossierMedicalService; // ajout

    public Patient createPatient(Patient patient, String groupeSanguin, Double poids, Double taille) {
        // Vérification couverture
        var resultat = verificationCouvertureService.verifier(
                patient.getCouvertureMedicale(),
                patient.getNumeroAssurance(),
                patient.getDateExpirationCouverture()
        );

        patient.setTauxPriseEnCharge(resultat.tauxPriseEnCharge());

        if (!resultat.valide()) {
            patient.setStatutAdmission(Patient.StatutAdmission.EN_ATTENTE);
        }

        // Si hospitalisation demandée et chambre précisée
        if (patient.getChambre() != null && !patient.getChambre().isBlank()) {
            try {
                chambreService.affecterLit(patient.getChambre());
                patient.setStatutAdmission(Patient.StatutAdmission.ADMIS);
            } catch (RuntimeException e) {
                throw new RuntimeException("Impossible d'affecter la chambre : " + e.getMessage());
            }
        }

        Patient saved = patientRepository.save(patient);

        // Création automatique du dossier médical
        DossierMedical dossier = new DossierMedical();
        dossier.setPatientId(saved.getId());
        dossier.setGroupeSanguin(groupeSanguin);
        dossier.setPoids(poids);
        dossier.setTaille(taille);
        dossierMedicalService.createDossier(dossier);

        return saved;
    }//

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Optional<Patient> getPatientByClinicalId(String clinicalId) {
        return patientRepository.findByClinicalId(clinicalId);
    }

    public List<Patient> searchPatients(String query) {
        return patientRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query);
    }

    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
        patient.setNom(patientDetails.getNom());
        patient.setPrenom(patientDetails.getPrenom());
        patient.setDateNaissance(patientDetails.getDateNaissance());
        patient.setSexe(patientDetails.getSexe());
        patient.setAdresse(patientDetails.getAdresse());
        patient.setTelephone(patientDetails.getTelephone());
        patient.setEmail(patientDetails.getEmail());
        patient.setCouvertureMedicale(patientDetails.getCouvertureMedicale());
        patient.setChambre(patientDetails.getChambre());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public long getPatientCount() {
        return patientRepository.count();
    }

    public Patient sortirPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
        if (patient.getChambre() != null) {
            chambreService.libererLit(patient.getChambre());
        }
        patient.setDateSortie(LocalDate.now());
        patient.setChambre(null); // il n'occupe plus de chambre
        patient.setServiceAffecte(null);
        return patientRepository.save(patient);
    }

    public Patient transfererPatient(Long id, String nouvelleChambre) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));
        String ancienneChambre = patient.getChambre();
        if (ancienneChambre != null && !ancienneChambre.equals(nouvelleChambre)) {
            chambreService.libererLit(ancienneChambre);
        }
        if (nouvelleChambre != null && !nouvelleChambre.isBlank()) {
            chambreService.affecterLit(nouvelleChambre);
        }
        patient.setChambre(nouvelleChambre);
        return patientRepository.save(patient);
    }

    public List<Patient> getPatientsHospitalises() {
        return patientRepository.findByChambreIsNotNullAndDateSortieIsNull();
    }

    public List<Patient> getInPatients() {
        return patientRepository.findByChambreIsNotNullAndDateSortieIsNull();
    }

    public List<Patient> getOutPatients() {
        return patientRepository.findByChambreIsNull();
    }

    public List<Patient> getPendingReview() {
        return patientRepository.findByStatutAdmission(Patient.StatutAdmission.EN_ATTENTE);
    }
}