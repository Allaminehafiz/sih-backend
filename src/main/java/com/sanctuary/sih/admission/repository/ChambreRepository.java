package com.sanctuary.sih.admission.repository;

import com.sanctuary.sih.admission.entity.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    Optional<Chambre> findByNumero(String numero);
    List<Chambre> findByServiceAndLitsOccupesLessThan(String service, int capacite);
    List<Chambre> findByLitsOccupesLessThan(int capacite);
}