package com.sanctuary.sih.facturation.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factures")
@Data @NoArgsConstructor @AllArgsConstructor
public class Facture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(unique = true)
    private String invoiceId;

    @Column(nullable = false)
    private LocalDate dateEmission;

    @Column(nullable = false)
    private Double montantTotal;

    private Double partMutuelle;   // nouvelle colonne
    private Double partPatient;    // nouvelle colonne
    private Double tauxCouverture; // ex: 0.8

    @Enumerated(EnumType.STRING)
    private StatutFacture statut = StatutFacture.EN_ATTENTE;

    private LocalDate dateEcheance;
    private String notes;

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneFacture> lignes = new ArrayList<>();

    @PrePersist
    public void generateInvoiceId() {
        if (this.invoiceId == null) {
            this.invoiceId = "INV-" + System.currentTimeMillis() % 1000000;
        }
        if (this.dateEmission == null) {
            this.dateEmission = LocalDate.now();
        }
    }

    public enum StatutFacture {
        PAYEE, EN_ATTENTE, EN_RETARD
    }

    @Transient
    private List<Paiement> paiements = new ArrayList<>();
}