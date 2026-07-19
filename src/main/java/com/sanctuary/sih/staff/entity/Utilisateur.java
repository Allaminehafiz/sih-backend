package com.sanctuary.sih.staff.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String telephone;

    private String departement;

    private String specialite;


    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String resetToken;


    @JsonInclude(JsonInclude.Include.ALWAYS)
    private LocalDateTime resetTokenExpiration;

    @Column(nullable = false)
    private boolean actif = true;

    public enum Role {
        MEDECIN, INFIRMIER, PHARMACIEN, COMPTABLE, ADMIN, AGENT_ADMISSION
    }
}