package com.sanctuary.sih.pharmacie.controller;

import com.sanctuary.sih.pharmacie.entity.Medicament;
import com.sanctuary.sih.pharmacie.service.MedicamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pharmacie/medicaments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MedicamentController {

    private final MedicamentService medicamentService;

    @PostMapping
    public ResponseEntity<Medicament> createMedicament(@RequestBody Medicament medicament) {
        return ResponseEntity.ok(medicamentService.createMedicament(medicament));
    }

    @GetMapping
    public ResponseEntity<List<Medicament>> getAllMedicaments() {
        return ResponseEntity.ok(medicamentService.getAllMedicaments());
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Medicament> updateStock(@PathVariable Long id, @RequestParam int quantite) {
        return ResponseEntity.ok(medicamentService.updateStock(id, quantite));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Medicament>> getLowStock() {
        return ResponseEntity.ok(medicamentService.getLowStockMedicaments());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable Long id) {
        medicamentService.deleteMedicament(id);
        return ResponseEntity.noContent().build();
    }
}