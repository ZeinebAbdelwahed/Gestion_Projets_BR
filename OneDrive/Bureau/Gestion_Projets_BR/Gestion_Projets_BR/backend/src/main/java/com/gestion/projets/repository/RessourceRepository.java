package com.gestion.projets.repository;

import com.gestion.projets.entity.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RessourceRepository extends JpaRepository<Ressource, Long> {

    List<Ressource> findByDisponibilite(Boolean disponibilite);

    List<Ressource> findByType(String type);
}
