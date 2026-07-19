package com.sanctuary.sih.medical.controller;

import com.sanctuary.sih.medical.entity.CreneauTravail;
import com.sanctuary.sih.medical.service.StaffService;
import com.sanctuary.sih.staff.entity.Utilisateur;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("api/staff")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/medecins")
    public ResponseEntity<Utilisateur> createMedecin(@RequestBody MedecinRequest request) {
        Utilisateur medecin = request.getUtilisateur();
        List<CreneauTravail> creneaux = request.getCreneaux();
        return ResponseEntity.ok(staffService.createMedecin(medecin, creneaux));
    }

    // Classe interne pour la requête
    @Data
    public static class MedecinRequest {
        private Utilisateur utilisateur;
        private List<CreneauTravail> creneaux;
    }
}
