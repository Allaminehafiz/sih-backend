package com.sanctuary.sih.facturation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long factureId;

    @Column(nullable = false)
    private LocalDate datePaiement;

    @Column(nullable = false)
    private Double montant;

    private String modePaiement;
}