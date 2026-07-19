package com.sanctuary.sih.facturation.repository;

import com.sanctuary.sih.facturation.entity.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneFactureRepository extends JpaRepository<LigneFacture, Long> {}