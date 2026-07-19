package com.sanctuary.sih.pharmacie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "ordonnances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ordonnance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long prescriptionId;

    @Column(nullable = false)
    private Long patientId;

    private LocalDate dateValidation;

    @Enumerated(EnumType.STRING)
    private StatutOrdonnance statut = StatutOrdonnance.EN_ATTENTE;

    public enum StatutOrdonnance {
        EN_ATTENTE, SERVIE
    }
}