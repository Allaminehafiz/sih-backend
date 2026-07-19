package com.sanctuary.sih.medical.service;

import com.sanctuary.sih.medical.entity.Prescription;
import com.sanctuary.sih.medical.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getPrescriptionsByConsultation(Long consultationId) {
        return prescriptionRepository.findByConsultationId(consultationId);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
}