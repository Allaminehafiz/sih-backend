package com.sanctuary.sih.pharmacie.service;

import com.sanctuary.sih.pharmacie.entity.Medicament;
import com.sanctuary.sih.pharmacie.repository.MedicamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentService {

    private final MedicamentRepository medicamentRepository;

    public Medicament createMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    public List<Medicament> getAllMedicaments() {
        return medicamentRepository.findAll();
    }

    public Medicament updateStock(Long id, int quantite) {
        Medicament medicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médicament non trouvé"));
        medicament.setQuantiteStock(quantite);
        return medicamentRepository.save(medicament);
    }

    public List<Medicament> getLowStockMedicaments() {
        return medicamentRepository.findAll().stream()
                .filter(m -> m.getQuantiteStock() <= m.getSeuilAlerte())
                .toList();
    }

    public void deleteMedicament(Long id) {
        medicamentRepository.deleteById(id);
    }
}