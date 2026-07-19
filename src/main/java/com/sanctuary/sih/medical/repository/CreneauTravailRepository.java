package com.sanctuary.sih.medical.repository;

import com.sanctuary.sih.medical.entity.CreneauTravail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CreneauTravailRepository extends JpaRepository<CreneauTravail, Long> {
    List<CreneauTravail> findByMedecinIdAndJourSemaine(Long medecinId, int jourSemaine);
    List<CreneauTravail> findByMedecinId(Long medecinId);
}