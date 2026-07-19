package com.sanctuary.sih.admission.controller;

import com.sanctuary.sih.admission.entity.Patient;
import com.sanctuary.sih.admission.service.PatientService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admission/patients")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.createPatient(
                request.getPatient(),
                request.getGroupeSanguin(),
                request.getPoids(),
                request.getTaille()
        ));
    }

    // DTO interne
    @Data
    public static class PatientRequest {
        private Patient patient;
        private String groupeSanguin;
        private Double poids;
        private Double taille;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/clinical/{clinicalId}")
    public ResponseEntity<Patient> getPatientByClinicalId(@PathVariable String clinicalId) {
        return patientService.getPatientByClinicalId(clinicalId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatients(@RequestParam String q) {
        return ResponseEntity.ok(patientService.searchPatients(q));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getPatientCount() {
        return ResponseEntity.ok(patientService.getPatientCount());
    }

    @PutMapping("/{id}/sortir")
    public ResponseEntity<Patient> sortirPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.sortirPatient(id));
    }

    @PutMapping("/{id}/transferer")
    public ResponseEntity<Patient> transfererPatient(
            @PathVariable Long id,
            @RequestParam String nouvelleChambre) {
        return ResponseEntity.ok(patientService.transfererPatient(id, nouvelleChambre));
    }

    @GetMapping("/hospitalises")
    public ResponseEntity<List<Patient>> getPatientsHospitalises() {
        return ResponseEntity.ok(patientService.getPatientsHospitalises());
    }

    @GetMapping("/in-patients")
    public ResponseEntity<List<Patient>> getInPatients() {
        return ResponseEntity.ok(patientService.getInPatients());
    }

    @GetMapping("/out-patients")
    public ResponseEntity<List<Patient>> getOutPatients() {
        return ResponseEntity.ok(patientService.getOutPatients());
    }

    @GetMapping("/pending-review")
    public ResponseEntity<List<Patient>> getPendingReview() {
        return ResponseEntity.ok(patientService.getPendingReview());
    }
}