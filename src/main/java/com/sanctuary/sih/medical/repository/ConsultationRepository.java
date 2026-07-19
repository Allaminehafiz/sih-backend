package com.sanctuary.sih.medical.repository;

import com.sanctuary.sih.medical.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByDossierIdOrderByDateConsultationDesc(Long dossierId);
    List<Consultation> findByMedecinId(Long medecinId);
    List<Consultation> findByDateConsultationBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT c FROM Consultation c WHERE c.medecinId = :medecinId " +
            "AND c.dateConsultation >= :debut AND c.dateConsultation < :fin")
    List<Consultation> findByMedecinIdAndDateConsultationBetween(
            @Param("medecinId") Long medecinId,
            @Param("debut") LocalDateTime debut,
            @Param("fin") LocalDateTime fin);

    Optional<Consultation> findTopByPatientIdAndFactureIdIsNotNullOrderByDateConsultationDesc(Long patientId);

    Optional<Consultation> findTopByPatientIdOrderByDateConsultationDesc(Long patientId);

    long countByDateConsultationBetween(LocalDateTime start, LocalDateTime end);

}