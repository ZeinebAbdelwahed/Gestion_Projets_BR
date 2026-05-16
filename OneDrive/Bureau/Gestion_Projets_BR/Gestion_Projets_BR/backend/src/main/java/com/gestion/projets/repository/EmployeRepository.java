package com.gestion.projets.repository;

import com.gestion.projets.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

    Optional<Employe> findByEmail(String email);

    List<Employe> findByEquipe(String equipe);

    List<Employe> findByRole(String role);
}
