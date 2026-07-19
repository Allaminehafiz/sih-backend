package com.sanctuary.sih.medical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long consultationId;

    @Column(nullable = false)
    private String medicament;

    @Column(nullable = false)
    private String dosage;

    private String instructions;

    @Column(nullable = false)
    private int quantite;

    private int nbRenouvellements;

    private LocalDate dateDebut;
}