package com.sanctuary.sih.medical.controller;

import com.sanctuary.sih.medical.entity.Prescription;
import com.sanctuary.sih.medical.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medical/prescriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        return ResponseEntity.ok(prescriptionService.createPrescription(prescription));
    }

    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<List<Prescription>> getByConsultation(@PathVariable Long consultationId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByConsultation(consultationId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}