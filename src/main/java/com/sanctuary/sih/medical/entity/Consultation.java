package com.sanctuary.sih.medical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long dossierId;

    @Column(nullable = false)
    private Long medecinId;

    @Column(nullable = false)
    private LocalDateTime dateConsultation;

    private String motif;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private int dureeMinutes = 30;

    @Column(nullable = false)
    private boolean urgence = false;

    private Long factureId; // nullable

    private Long patientId;

}