package com.sanctuary.sih.facturation.repository;

import com.sanctuary.sih.facturation.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    Optional<Facture> findByInvoiceId(String invoiceId);
    List<Facture> findByPatientId(Long patientId);
    List<Facture> findByStatut(Facture.StatutFacture statut);
    long countByStatut(Facture.StatutFacture statut);

}
