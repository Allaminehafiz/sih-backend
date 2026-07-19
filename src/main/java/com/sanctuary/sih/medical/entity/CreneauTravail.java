package com.sanctuary.sih.medical.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "creneaux_travail")
@Data @NoArgsConstructor @AllArgsConstructor
public class CreneauTravail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long medecinId;

    @Column(nullable = false)
    private int jourSemaine; // 1=Lundi .. 7=Dimanche

    @Column(nullable = false)
    private LocalTime heureDebut;

    @Column(nullable = false)
    private LocalTime heureFin;
}