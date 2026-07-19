package com.sanctuary.sih.facturation.controller;

import com.sanctuary.sih.facturation.entity.Paiement;
import com.sanctuary.sih.facturation.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturation/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping
    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) {
        return ResponseEntity.ok(paiementService.createPaiement(paiement));
    }

    @GetMapping("/facture/{factureId}")
    public ResponseEntity<List<Paiement>> getPaiementsByFacture(@PathVariable Long factureId) {
        return ResponseEntity.ok(paiementService.getPaiementsByFacture(factureId));
    }
}