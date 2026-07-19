package com.sanctuary.sih.staff.controller;

import com.sanctuary.sih.staff.entity.Utilisateur;
import com.sanctuary.sih.staff.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/staff/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(utilisateurService.createUtilisateur(utilisateur));
    }

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Utilisateur>> getByRole(@PathVariable String role) {
        Utilisateur.Role roleEnum = Utilisateur.Role.valueOf(role.toUpperCase());
        return ResponseEntity.ok(utilisateurService.getByRole(roleEnum));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(utilisateurService.updateUtilisateur(id, utilisateur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }


}