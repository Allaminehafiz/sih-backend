package com.sanctuary.sih.pharmacie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicaments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    @Column(nullable = false)
    private int quantiteStock;

    private int seuilAlerte;

    private Double prixUnitaire;
}