package com.sanctuary.sih.pharmacie.controller;

import com.sanctuary.sih.pharmacie.entity.Ordonnance;
import com.sanctuary.sih.pharmacie.service.OrdonnanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pharmacie/ordonnances")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;

    @PostMapping
    public ResponseEntity<Ordonnance> createOrdonnance(@RequestBody Ordonnance ordonnance) {
        return ResponseEntity.ok(ordonnanceService.createOrdonnance(ordonnance));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Ordonnance>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(ordonnanceService.getOrdonnancesByPatient(patientId));
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<Ordonnance> validerOrdonnance(@PathVariable Long id) {
        return ResponseEntity.ok(ordonnanceService.validerOrdonnance(id));
    }
}