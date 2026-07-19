package com.sanctuary.sih.medical.repository;

import com.sanctuary.sih.medical.entity.Indisponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface IndisponibiliteRepository extends JpaRepository<Indisponibilite, Long> {
    @Query("SELECT i FROM Indisponibilite i WHERE i.medecinId = :medecinId " +
            "AND i.dateDebut < :fin AND i.dateFin > :debut")
    List<Indisponibilite> findChevauchements(Long medecinId, LocalDateTime debut, LocalDateTime fin);
}