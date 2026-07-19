package com.sanctuary.sih.medical.controller;

import com.sanctuary.sih.medical.entity.DossierMedical;
import com.sanctuary.sih.medical.service.DossierMedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medical/dossiers")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "https://chu-health.vercel.app"})
public class DossierMedicalController {

    private final DossierMedicalService dossierMedicalService;

    @PostMapping
    public ResponseEntity<DossierMedical> createDossier(@RequestBody DossierMedical dossier) {
        return ResponseEntity.ok(dossierMedicalService.createDossier(dossier));
    }

    @GetMapping
    public ResponseEntity<List<DossierMedical>> getAllDossiers() {
        return ResponseEntity.ok(dossierMedicalService.getAllDossiers());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<DossierMedical> getByPatientId(@PathVariable Long patientId) {
        return dossierMedicalService.getDossierByPatientId(patientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DossierMedical> updateDossier(@PathVariable Long id, @RequestBody DossierMedical dossier) {
        return ResponseEntity.ok(dossierMedicalService.updateDossier(id, dossier));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        dossierMedicalService.deleteDossier(id);
        return ResponseEntity.noContent().build();
    }

    
}