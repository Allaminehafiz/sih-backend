package com.sanctuary.sih.facturation.controller;

import com.sanctuary.sih.facturation.entity.Facture;
import com.sanctuary.sih.facturation.service.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/facturation/factures")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "https://chu-health.vercel.app"})
public class FactureController {

    private final FactureService factureService;

    @PostMapping
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        return ResponseEntity.ok(factureService.createFacture(facture));
    }

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Facture>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(factureService.getFacturesByPatient(patientId));
    }

    @GetMapping("/statut")
    public ResponseEntity<List<Facture>> getByStatut(@RequestParam Facture.StatutFacture statut) {
        return ResponseEntity.ok(factureService.getFacturesByStatut(statut));
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<Facture> updateStatut(@PathVariable Long id, @RequestParam Facture.StatutFacture statut) {
        return ResponseEntity.ok(factureService.updateStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generer/{patientId}")
    public ResponseEntity<Facture> genererFacture(@PathVariable Long patientId) {
        try {
            return ResponseEntity.ok(factureService.genererFactureAutomatique(patientId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<Facture> getFactureDetail(@PathVariable Long id) {
        return ResponseEntity.ok(factureService.getFactureDetail(id));
    }
}