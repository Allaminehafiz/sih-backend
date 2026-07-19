package com.sanctuary.sih.admission.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chambres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chambre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero; // ex: "201", "B12"

    private String service; // ex: "Cardiologie"

    private int capacite; // nombre total de lits

    private int litsOccupes; // lits actuellement occupés

    private String etage; // ex: "2ème étage"
}