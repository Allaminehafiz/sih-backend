package com.sanctuary.sih.admission.service;

import com.sanctuary.sih.admission.entity.Chambre;
import com.sanctuary.sih.admission.repository.ChambreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChambreService {

    private final ChambreRepository chambreRepository;

    public List<Chambre> getChambresDisponibles(String service) {
        return chambreRepository.findByServiceAndLitsOccupesLessThan(service, 1);
        // Retourne les chambres où litsOccupes < capacite (donc au moins 1 lit libre)
    }

    public List<Chambre> getChambresDisponibles() {
        return chambreRepository.findByLitsOccupesLessThan(1);
    }

    public Chambre affecterLit(String numeroChambre) {
        Chambre chambre = chambreRepository.findByNumero(numeroChambre)
                .orElseThrow(() -> new RuntimeException("Chambre introuvable : " + numeroChambre));
        if (chambre.getLitsOccupes() >= chambre.getCapacite()) {
            throw new RuntimeException("Aucun lit disponible dans la chambre " + numeroChambre);
        }
        chambre.setLitsOccupes(chambre.getLitsOccupes() + 1);
        return chambreRepository.save(chambre);
    }

    public Chambre libererLit(String numeroChambre) {
        Chambre chambre = chambreRepository.findByNumero(numeroChambre)
                .orElseThrow(() -> new RuntimeException("Chambre introuvable : " + numeroChambre));
        if (chambre.getLitsOccupes() > 0) {
            chambre.setLitsOccupes(chambre.getLitsOccupes() - 1);
        }
        return chambreRepository.save(chambre);
    }

    public Chambre createChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }
}