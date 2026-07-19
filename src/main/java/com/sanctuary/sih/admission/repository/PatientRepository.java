package com.sanctuary.sih.admission.repository;

import com.sanctuary.sih.admission.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByClinicalId(String clinicalId);
    List<Patient> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
    List<Patient> findBySexe(String sexe);
    long count();
    List<Patient> findByChambreIsNotNullAndDateSortieIsNull();

    List<Patient> findTop5ByOrderByDateAdmissionDesc();

    List<Patient> findByChambreIsNull();
    List<Patient> findByStatutAdmission(Patient.StatutAdmission statutAdmission);
}