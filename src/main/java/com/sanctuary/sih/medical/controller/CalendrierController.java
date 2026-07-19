package com.sanctuary.sih.medical.controller;

import com.sanctuary.sih.medical.service.DisponibiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendrierController {

    private final DisponibiliteService disponibiliteService;

    @GetMapping("/creneaux-libres")
    public ResponseEntity<List<DisponibiliteService.PlageHoraire>> getCreneauxLibres(
            @RequestParam Long medecinId,
            @RequestParam String date,
            @RequestParam(defaultValue = "30") int duree) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(disponibiliteService.getCreneauxLibres(medecinId, localDate, duree));
    }
}