package com.sanctuary.sih.facturation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lignes_facture")
@Data @NoArgsConstructor @AllArgsConstructor
public class LigneFacture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;   // ex: "Consultation cardiologie"
    private Double montantUnitaire;
    private int quantite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facture_id")
    @JsonIgnore
    private Facture facture;
}
