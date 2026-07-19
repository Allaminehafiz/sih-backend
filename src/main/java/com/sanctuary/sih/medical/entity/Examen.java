package com.sanctuary.sih.medical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "examens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Double prix;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String typeExamen;

    @Column(nullable = false)
    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private StatutExamen statut = StatutExamen.PENDING;

    @Column(columnDefinition = "TEXT")
    private String resultat;

    public enum StatutExamen {
        PENDING, FINALIZED
    }
    private Long factureId;
}