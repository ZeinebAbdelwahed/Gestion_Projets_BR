package com.gestion.projets.service;

import com.gestion.projets.convertor.RessourceConvertor;
import com.gestion.projets.dto.RessourceDTO;
import com.gestion.projets.entity.Ressource;
import com.gestion.projets.repository.RessourceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RessourceService {

    private final RessourceRepository ressourceRepository;
    private final RessourceConvertor ressourceConvertor;

    public RessourceService(RessourceRepository ressourceRepository, RessourceConvertor ressourceConvertor) {
        this.ressourceRepository = ressourceRepository;
        this.ressourceConvertor = ressourceConvertor;
    }

    public List<RessourceDTO> findAll() {
        return ressourceConvertor.toListDto(ressourceRepository.findAll());
    }

    public RessourceDTO findById(Long id) {
        Ressource ressource = ressourceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ressource introuvable avec id: " + id));
        return ressourceConvertor.toDto(ressource);
    }

    public RessourceDTO create(RessourceDTO dto) {
        Ressource ressource = ressourceConvertor.fromDto(dto);
        ressource = ressourceRepository.save(ressource);
        return ressourceConvertor.toDto(ressource);
    }

    public RessourceDTO update(Long id, RessourceDTO dto) {
        Ressource ressource = ressourceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ressource introuvable avec id: " + id));

        ressource.setNom(dto.getNom());
        ressource.setType(dto.getType());
        ressource.setCout(dto.getCout());
        ressource.setDisponibilite(dto.getDisponibilite());

        ressource = ressourceRepository.save(ressource);
        return ressourceConvertor.toDto(ressource);
    }

    public void delete(Long id) {
        if (!ressourceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ressource introuvable avec id: " + id);
        }
        ressourceRepository.deleteById(id);
    }

    public List<RessourceDTO> findByDisponibilite(Boolean disponibilite) {
        return ressourceConvertor.toListDto(ressourceRepository.findByDisponibilite(disponibilite));
    }
}
