package com.sanctuary.sih.facturation.repository;

import com.sanctuary.sih.facturation.entity.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long> {
    Optional<Tarif> findByCodeActe(String codeActe);
}
