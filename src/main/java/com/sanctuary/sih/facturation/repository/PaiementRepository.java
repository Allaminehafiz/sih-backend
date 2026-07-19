package com.sanctuary.sih.facturation.repository;

import com.sanctuary.sih.facturation.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByFactureId(Long factureId);

    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p WHERE p.factureId = :factureId")
    double sumMontantByFactureId(@Param("factureId") Long factureId);

}