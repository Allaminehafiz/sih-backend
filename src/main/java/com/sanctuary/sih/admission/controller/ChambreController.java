package com.sanctuary.sih.admission.controller;

import com.sanctuary.sih.admission.entity.Chambre;
import com.sanctuary.sih.admission.service.ChambreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admission/chambres")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ChambreController {

    private final ChambreService chambreService;

    @GetMapping
    public List<Chambre> getAll() {
        return chambreService.getAllChambres();
    }

    @GetMapping("/disponibles")
    public List<Chambre> getDisponibles(@RequestParam(required = false) String service) {
        if (service != null) return chambreService.getChambresDisponibles(service);
        return chambreService.getChambresDisponibles();
    }

    @PostMapping
    public Chambre create(@RequestBody Chambre chambre) {
        return chambreService.createChambre(chambre);
    }

    @PutMapping("/{numero}/affecter")
    public Chambre affecter(@PathVariable String numero) {
        return chambreService.affecterLit(numero);
    }

    @PutMapping("/{numero}/liberer")
    public Chambre liberer(@PathVariable String numero) {
        return chambreService.libererLit(numero);
    }
}