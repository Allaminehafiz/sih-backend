package com.sanctuary.sih.admission.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Column(nullable = false)
    private String sexe;

    private String adresse;

    @Column(nullable = false)
    private String telephone;

    private String email;

    // Nouveaux champs couverture
    private String couvertureMedicale; // ex: "CNPS", "Mutuelle", "Assurance Privée", "Aucune"
    private String numeroAssurance;
    private LocalDate dateExpirationCouverture;
    private Double tauxPriseEnCharge; // 0.0 à 1.0, ex: 0.8 = 80%

    // Hospitalisation
    private String chambre; // numéro de chambre si hospitalisé
    private String serviceAffecte; // ex: "Cardiologie", "Chirurgie"

    private LocalDate dateSortie; // si renseignée, le patient est sorti

    @Enumerated(EnumType.STRING)
    private StatutAdmission statutAdmission = StatutAdmission.ADMIS;

    @Column(unique = true)
    private String clinicalId;

    @Column(nullable = false)
    private LocalDate dateAdmission;

    public enum StatutAdmission {
        EN_ATTENTE, // couverture invalide ou en attente de paiement
        ADMIS,
        REFUSE
    }

    @PrePersist
    public void generateClinicalId() {
        if (this.clinicalId == null) {
            this.clinicalId = "CS-" + System.currentTimeMillis() % 100000;
        }
        if (this.dateAdmission == null) {
            this.dateAdmission = LocalDate.now();
        }
        if (this.statutAdmission == null) {
            this.statutAdmission = StatutAdmission.ADMIS;
        }
    }
}