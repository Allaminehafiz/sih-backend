package com.sanctuary.sih.facturation.service;

import com.sanctuary.sih.admission.entity.Patient;
import com.sanctuary.sih.admission.repository.PatientRepository;
import com.sanctuary.sih.facturation.entity.Facture;
import com.sanctuary.sih.facturation.entity.LigneFacture;
import com.sanctuary.sih.facturation.entity.Paiement;
import com.sanctuary.sih.facturation.entity.Tarif;
import com.sanctuary.sih.facturation.repository.FactureRepository;
import com.sanctuary.sih.facturation.repository.PaiementRepository;
import com.sanctuary.sih.facturation.repository.TarifRepository;
import com.sanctuary.sih.medical.entity.Consultation;
import com.sanctuary.sih.medical.entity.Examen;
import com.sanctuary.sih.medical.repository.ConsultationRepository;
import com.sanctuary.sih.medical.repository.ExamenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;
    private final TarifRepository tarifRepository;
    private final PatientRepository patientRepository;
    private final ConsultationRepository consultationRepository;
    private final ExamenRepository examenRepository; // nouveau
    private final PaiementRepository paiementRepository;

    public Facture genererFactureAutomatique(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        // Dernière consultation (peu importe facturation)
        Optional<Consultation> derniereConsultation =
                consultationRepository.findTopByPatientIdOrderByDateConsultationDesc(patientId);

        boolean ajouterConsultation = true;
        if (derniereConsultation.isPresent()) {
            LocalDate dateDerniereConsult = derniereConsultation.get().getDateConsultation().toLocalDate();
            if (dateDerniereConsult.isAfter(LocalDate.now().minusDays(30))) {
                ajouterConsultation = false; // consultation récente, on ne refacture pas
            }
        }

        Tarif tarifConsultation = tarifRepository.findByCodeActe("CONSULTATION")
                .orElse(new Tarif(null, "CONSULTATION", "Consultation", 5000.0));

        Facture facture = new Facture();
        facture.setPatientId(patientId);
        facture.setDateEmission(LocalDate.now());
        facture.setDateEcheance(LocalDate.now().plusDays(30));
        double total = 0.0;

        // Ajout consultation si nécessaire
        if (ajouterConsultation) {
            LigneFacture ligneConsult = new LigneFacture();
            ligneConsult.setDescription(tarifConsultation.getLibelle());
            ligneConsult.setMontantUnitaire(tarifConsultation.getPrix());
            ligneConsult.setQuantite(1);
            ligneConsult.setFacture(facture);
            facture.getLignes().add(ligneConsult);
            total += tarifConsultation.getPrix();
            facture.setNotes("Consultation incluse automatiquement (aucune consultation récente)");
        } else {
            facture.setNotes("Consultation récente déjà effectuée. Seuls les examens sont facturés.");
        }

        // Ajout des examens non facturés
        List<Examen> examens = examenRepository.findByPatientIdAndFactureIdIsNull(patientId);
        for (Examen examen : examens) {
            double prixExamen = examen.getPrix() != null ? examen.getPrix() : 0.0;
            if (prixExamen > 0) {
                LigneFacture ligneExamen = new LigneFacture();
                ligneExamen.setDescription("Examen : " + examen.getTypeExamen());
                ligneExamen.setMontantUnitaire(prixExamen);
                ligneExamen.setQuantite(1);
                ligneExamen.setFacture(facture);
                facture.getLignes().add(ligneExamen);
                total += prixExamen;
            }
        }

        if (total == 0.0) {
            throw new RuntimeException("Aucun acte à facturer (aucun examen en attente et aucune consultation nécessaire).");
        }

        double taux = patient.getTauxPriseEnCharge() != null ? patient.getTauxPriseEnCharge() : 0.0;
        double partMutuelle = total * taux;
        double partPatient = total - partMutuelle;

        facture.setMontantTotal(total);
        facture.setPartMutuelle(partMutuelle);
        facture.setPartPatient(partPatient);
        facture.setTauxCouverture(taux);

        Facture savedFacture = factureRepository.save(facture);

        // Association des examens à la facture
        for (Examen examen : examens) {
            examen.setFactureId(savedFacture.getId());
            examenRepository.save(examen);
        }

        return savedFacture;
    }

    public Facture createFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public List<Facture> getFacturesByPatient(Long patientId) {
        return factureRepository.findByPatientId(patientId);
    }

    public List<Facture> getFacturesByStatut(Facture.StatutFacture statut) {
        return factureRepository.findByStatut(statut);
    }

    public Facture updateStatut(Long id, Facture.StatutFacture statut) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
        facture.setStatut(statut);
        return factureRepository.save(facture);
    }

    public long getPendingCount() {
        return factureRepository.countByStatut(Facture.StatutFacture.EN_ATTENTE);
    }

    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }

    public Facture getFactureDetail(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
        // Force le chargement des lignes et des paiements (si lazy)
        facture.getLignes().size();
        List<Paiement> paiements = paiementRepository.findByFactureId(id);
        facture.setPaiements(paiements); // vous pouvez ajouter un champ transient dans Facture
        return facture;
    }
}