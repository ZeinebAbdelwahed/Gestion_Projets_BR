package com.gestion.projets.repository;

import com.gestion.projets.entity.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {

    List<Tache> findByProjetId(Long projetId);

    List<Tache> findByEtat(String etat);

    List<Tache> findByPriorite(String priorite);
}
