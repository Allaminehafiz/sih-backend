package com.sanctuary.sih.medical.controller;

import com.sanctuary.sih.medical.entity.Examen;
import com.sanctuary.sih.medical.service.ExamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medical/examens")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ExamenController {

    private final ExamenService examenService;

    @PostMapping
    public ResponseEntity<Examen> createExamen(@RequestBody Examen examen) {
        return ResponseEntity.ok(examenService.createExamen(examen));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Examen>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(examenService.getExamensByPatient(patientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Examen> updateExamen(@PathVariable Long id, @RequestBody Examen examen) {
        return ResponseEntity.ok(examenService.updateExamen(id, examen));
    }
}