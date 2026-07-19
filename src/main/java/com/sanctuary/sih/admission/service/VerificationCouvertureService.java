package com.sanctuary.sih.admission.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Map;

@Service
public class VerificationCouvertureService {

    private static final Map<String, Double> TAUX_COUVERTURE = Map.of(
            "CNPS", 0.8,
            "MUTUELLE", 0.7,
            "ASSURANCE_PRIVEE", 0.9
    );

    public ResultatVerification verifier(String typeCouverture, String numeroAssurance, LocalDate dateExpiration) {
        // Si pas de couverture du tout : ADMIS normal, taux 0
        if (typeCouverture == null || typeCouverture.isBlank() || typeCouverture.equalsIgnoreCase("Aucune")) {
            return new ResultatVerification(true, 0.0, "Aucune couverture");
        }

        // Si la date d'expiration est dépassée : EN_ATTENTE
        if (dateExpiration != null && dateExpiration.isBefore(LocalDate.now())) {
            return new ResultatVerification(false, 0.0, "Couverture expirée");
        }

        // Couverture valide
        Double taux = TAUX_COUVERTURE.getOrDefault(typeCouverture.toUpperCase(), 0.0);
        if (taux == 0.0) {
            return new ResultatVerification(false, 0.0, "Type de couverture inconnu");
        }

        return new ResultatVerification(true, taux, "Couverture valide");
    }

    public record ResultatVerification(boolean valide, double tauxPriseEnCharge, String message) {}
}