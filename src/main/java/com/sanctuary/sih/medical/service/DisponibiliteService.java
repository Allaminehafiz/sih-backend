package com.sanctuary.sih.medical.service;

import com.sanctuary.sih.medical.entity.*;
import com.sanctuary.sih.medical.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DisponibiliteService {

    private final CreneauTravailRepository creneauRepo;
    private final IndisponibiliteRepository indispoRepo;
    private final ConsultationRepository consultationRepo;

    public boolean estDisponible(Long medecinId, LocalDateTime debut, LocalDateTime fin) {
        DayOfWeek jour = debut.getDayOfWeek();
        int jourSemaine = jour.getValue();
        List<CreneauTravail> creneaux = creneauRepo.findByMedecinIdAndJourSemaine(medecinId, jourSemaine);
        boolean dansCreneauTravail = creneaux.stream().anyMatch(c ->
                !debut.toLocalTime().isBefore(c.getHeureDebut()) &&
                        !fin.toLocalTime().isAfter(c.getHeureFin())
        );
        if (!dansCreneauTravail) return false;

        List<Indisponibilite> indispos = indispoRepo.findChevauchements(medecinId, debut, fin);
        if (!indispos.isEmpty()) return false;

        List<Consultation> conflits = consultationRepo.findByMedecinIdAndDateConsultationBetween(medecinId, debut, fin);
        return conflits.isEmpty();
    }

    public List<PlageHoraire> getCreneauxLibres(Long medecinId, LocalDate date, int dureeMinutes) {
        List<PlageHoraire> resultats = new ArrayList<>();
        DayOfWeek jour = date.getDayOfWeek();
        int jourSemaine = jour.getValue();
        List<CreneauTravail> creneaux = creneauRepo.findByMedecinIdAndJourSemaine(medecinId, jourSemaine);

        for (CreneauTravail ct : creneaux) {
            LocalDateTime debutJournee = LocalDateTime.of(date, ct.getHeureDebut());
            LocalDateTime finJournee = LocalDateTime.of(date, ct.getHeureFin());
            LocalDateTime creneauDebut = debutJournee;

            while (!creneauDebut.plusMinutes(dureeMinutes).isAfter(finJournee)) {
                LocalDateTime creneauFin = creneauDebut.plusMinutes(dureeMinutes);
                if (estDisponible(medecinId, creneauDebut, creneauFin)) {
                    resultats.add(new PlageHoraire(creneauDebut.toLocalTime(), creneauFin.toLocalTime()));
                }
                creneauDebut = creneauDebut.plusMinutes(30);
            }
        }
        return resultats;
    }

    public record PlageHoraire(LocalTime debut, LocalTime fin) {}
}