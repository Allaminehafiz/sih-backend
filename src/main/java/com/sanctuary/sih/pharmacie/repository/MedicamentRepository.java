package com.sanctuary.sih.pharmacie.repository;

import com.sanctuary.sih.pharmacie.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    Optional<Medicament> findByNom(String nom);
    List<Medicament> findByQuantiteStockLessThanEqual(int seuil);
}