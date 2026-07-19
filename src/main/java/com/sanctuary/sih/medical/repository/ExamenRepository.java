package com.sanctuary.sih.medical.repository;

import com.sanctuary.sih.medical.entity.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    List<Examen> findByPatientId(Long patientId);
    List<Examen> findByStatut(Examen.StatutExamen statut);
    List<Examen> findByPatientIdAndFactureIdIsNull(Long patientId);


}