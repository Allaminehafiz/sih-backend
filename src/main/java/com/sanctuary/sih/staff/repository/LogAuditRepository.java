package com.sanctuary.sih.staff.repository;

import com.sanctuary.sih.staff.entity.LogAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogAuditRepository extends JpaRepository<LogAudit, Long> {
    List<LogAudit> findByUtilisateurId(Long utilisateurId);
    List<LogAudit> findTop100ByOrderByDateActionDesc();
}