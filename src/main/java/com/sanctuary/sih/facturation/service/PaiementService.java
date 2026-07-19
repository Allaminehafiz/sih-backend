package com.sanctuary.sih.facturation.service;

import com.sanctuary.sih.facturation.entity.Facture;
import com.sanctuary.sih.facturation.entity.Paiement;
import com.sanctuary.sih.facturation.repository.FactureRepository;
import com.sanctuary.sih.facturation.repository.PaiementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final FactureRepository factureRepository;

    public Paiement createPaiement(Paiement paiement) {
        // Sauvegarde du paiement
        Paiement saved = paiementRepository.save(paiement);

        // Vérifier si la facture est maintenant totalement payée
        Facture facture = factureRepository.findById(paiement.getFactureId())
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

        double totalPaye = paiementRepository.sumMontantByFactureId(facture.getId());
        if (totalPaye >= facture.getMontantTotal()) {
            facture.setStatut(Facture.StatutFacture.PAYEE);
            factureRepository.save(facture);
        }

        return saved;
    }

    public List<Paiement> getPaiementsByFacture(Long factureId) {
        return paiementRepository.findByFactureId(factureId);
    }
}