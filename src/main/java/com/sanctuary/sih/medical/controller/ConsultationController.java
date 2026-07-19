package com.sanctuary.sih.medical.controller;

import com.sanctuary.sih.medical.entity.Consultation;
import com.sanctuary.sih.medical.service.ConsultationService;
import com.sanctuary.sih.medical.service.DisponibiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/medical/consultations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ConsultationController {

    private final ConsultationService consultationService;
    private final DisponibiliteService disponibiliteService;
    @PostMapping
    public ResponseEntity<?> createConsultation(@RequestBody Consultation consultation) {
        if (!consultation.isUrgence()) {
            LocalDateTime fin = consultation.getDateConsultation().plusMinutes(consultation.getDureeMinutes());
            if (!disponibiliteService.estDisponible(consultation.getMedecinId(), consultation.getDateConsultation(), fin)) {
                return ResponseEntity.status(409).body("Créneau non disponible");
            }
        }
        return ResponseEntity.ok(consultationService.createConsultation(consultation));
    }

    @GetMapping("/dossier/{dossierId}")
    public ResponseEntity<List<Consultation>> getByDossier(@PathVariable Long dossierId) {
        return ResponseEntity.ok(consultationService.getConsultationsByDossier(dossierId));
    }

    @GetMapping("/medecin/{medecinId}")
    public ResponseEntity<List<Consultation>> getByMedecin(@PathVariable Long medecinId) {
        return ResponseEntity.ok(consultationService.getConsultationsByMedecin(medecinId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable Long id, @RequestBody Consultation consultation) {
        return ResponseEntity.ok(consultationService.updateConsultation(id, consultation));
    }

    @GetMapping("/range")
    public ResponseEntity<List<Consultation>> getByDateRange(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return ResponseEntity.ok(consultationService.getConsultationsBetween(startDate, endDate));
    }
}