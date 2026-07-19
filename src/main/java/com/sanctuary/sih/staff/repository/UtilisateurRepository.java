package com.sanctuary.sih.staff.repository;

import com.sanctuary.sih.staff.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Utilisateur> findByRole(Utilisateur.Role role);
    List<Utilisateur> findByDepartement(String departement);
    Optional<Utilisateur> findByResetToken(String resetToken);
}