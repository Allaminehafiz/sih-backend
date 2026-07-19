package com.sanctuary.sih.dashboard;

import com.sanctuary.sih.admission.repository.PatientRepository;
import com.sanctuary.sih.medical.repository.ConsultationRepository;
import com.sanctuary.sih.facturation.repository.FactureRepository;
import com.sanctuary.sih.facturation.entity.Facture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final PatientRepository patientRepository;
    private final ConsultationRepository consultationRepository;
    private final FactureRepository factureRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalPatients = patientRepository.count();
        stats.put("totalPatients", totalPatients);

        LocalDate today = LocalDate.now();
        long appointmentsToday = consultationRepository.countByDateConsultationBetween(
                today.atStartOfDay(), today.plusDays(1).atStartOfDay()
        );
        stats.put("appointmentsToday", appointmentsToday);

        long pendingInvoices = factureRepository.countByStatut(Facture.StatutFacture.EN_ATTENTE);
        stats.put("pendingInvoices", pendingInvoices);

        long overdueInvoices = factureRepository.countByStatut(Facture.StatutFacture.EN_RETARD);
        stats.put("overdueInvoices", overdueInvoices);

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/montant-en-attente")
    public ResponseEntity<Double> getMontantEnAttente() {
        List<Facture> factures = factureRepository.findByStatut(Facture.StatutFacture.EN_ATTENTE);
        double total = factures.stream().mapToDouble(Facture::getMontantTotal).sum();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/recent-patients")
    public ResponseEntity<List<Map<String, Object>>> getRecentPatients() {
        var patients = patientRepository.findTop5ByOrderByDateAdmissionDesc();
        var result = patients.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", p.getPrenom() + " " + p.getNom());
            map.put("clinicalId", p.getClinicalId());
            map.put("dateAdmission", p.getDateAdmission() != null ? p.getDateAdmission().toString() : "N/A");
            map.put("status", p.getStatutAdmission() != null ? p.getStatutAdmission().name() : "ADMIS");
            return map;
        }).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/stats-facturation")
    public ResponseEntity<Map<String, Object>> getStatsFacturation() {
        Map<String, Object> stats = new HashMap<>();

        // Somme totale des factures payées (revenu)
        List<Facture> facturesPayees = factureRepository.findByStatut(Facture.StatutFacture.PAYEE);
        double revenuTotal = facturesPayees.stream().mapToDouble(Facture::getMontantTotal).sum();

        // Somme des factures en attente
        List<Facture> facturesEnAttente = factureRepository.findByStatut(Facture.StatutFacture.EN_ATTENTE);
        double montantEnAttente = facturesEnAttente.stream().mapToDouble(Facture::getMontantTotal).sum();

        // Nombre de factures en attente
        long nombreEnAttente = facturesEnAttente.size();

        // Nombre de factures en retard
        long nombreEnRetard = factureRepository.countByStatut(Facture.StatutFacture.EN_RETARD);

        stats.put("revenuTotal", revenuTotal);
        stats.put("montantEnAttente", montantEnAttente);
        stats.put("nombreEnAttente", nombreEnAttente);
        stats.put("nombreEnRetard", nombreEnRetard);

        return ResponseEntity.ok(stats);
    }
}