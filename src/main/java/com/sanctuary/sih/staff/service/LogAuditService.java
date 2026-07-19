package com.sanctuary.sih.staff.service;

import com.sanctuary.sih.staff.entity.LogAudit;
import com.sanctuary.sih.staff.repository.LogAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogAuditService {

    private final LogAuditRepository logAuditRepository;

    public LogAudit logAction(Long utilisateurId, String action, String details) {
        LogAudit log = new LogAudit();
        log.setUtilisateurId(utilisateurId);
        log.setAction(action);
        log.setDetails(details);
        return logAuditRepository.save(log);
    }

    public List<LogAudit> getRecentLogs() {
        return logAuditRepository.findTop100ByOrderByDateActionDesc();
    }
}