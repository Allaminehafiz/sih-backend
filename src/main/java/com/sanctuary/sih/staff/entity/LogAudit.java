package com.sanctuary.sih.staff.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long utilisateurId;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private LocalDateTime dateAction;

    @Column(columnDefinition = "TEXT")
    private String details;

    @PrePersist
    public void setDateAction() {
        if (this.dateAction == null) {
            this.dateAction = LocalDateTime.now();
        }
    }
}