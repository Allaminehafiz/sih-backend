package com.sanctuary.sih.facturation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tarifs")
@Data @NoArgsConstructor @AllArgsConstructor
public class Tarif {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codeActe;     // ex: "CONSULTATION", "IRM"

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private Double prix;
}