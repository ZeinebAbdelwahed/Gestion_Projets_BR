package com.gestion.projets.service;

import com.gestion.projets.convertor.TacheConvertor;
import com.gestion.projets.dto.TacheDTO;
import com.gestion.projets.entity.Employe;
import com.gestion.projets.entity.Projet;
import com.gestion.projets.entity.Ressource;
import com.gestion.projets.entity.Tache;
import com.gestion.projets.repository.EmployeRepository;
import com.gestion.projets.repository.ProjetRepository;
import com.gestion.projets.repository.RessourceRepository;
import com.gestion.projets.repository.TacheRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class TacheService {

    private final TacheRepository tacheRepository;
    private final ProjetRepository projetRepository;
    private final EmployeRepository employeRepository;
    private final RessourceRepository ressourceRepository;
    private final TacheConvertor tacheConvertor;

    public TacheService(TacheRepository tacheRepository, ProjetRepository projetRepository,
                        EmployeRepository employeRepository, RessourceRepository ressourceRepository,
                        TacheConvertor tacheConvertor) {
        this.tacheRepository = tacheRepository;
        this.projetRepository = projetRepository;
        this.employeRepository = employeRepository;
        this.ressourceRepository = ressourceRepository;
        this.tacheConvertor = tacheConvertor;
    }

    @Transactional(readOnly = true)
    public List<TacheDTO> findAll() {
        return tacheConvertor.toListDto(tacheRepository.findAll());
    }

    @Transactional(readOnly = true)
    public TacheDTO findById(Long id) {
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tache introuvable avec id: " + id));
        return tacheConvertor.toDto(tache);
    }

    @Transactional(readOnly = true)
    public List<TacheDTO> findByProjetId(Long projetId) {
        return tacheConvertor.toListDto(tacheRepository.findByProjetId(projetId));
    }

    @Transactional
    public TacheDTO create(TacheDTO dto) {
        Tache tache = buildEntity(dto);
        validateDeadline(dto.getDeadline(), tache.getProjet());
        tache = tacheRepository.save(tache);
        return tacheConvertor.toDto(tache);
    }

    @Transactional
    public TacheDTO update(Long id, TacheDTO dto) {
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tache introuvable avec id: " + id));

        Projet projet = projetRepository.findById(dto.getProjetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Projet introuvable avec id: " + dto.getProjetId()));
        tache.setProjet(projet);

        if (dto.getResponsableId() != null) {
            Employe responsable = employeRepository.findById(dto.getResponsableId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Employe introuvable avec id: " + dto.getResponsableId()));
            tache.setResponsable(responsable);
        } else {
            tache.setResponsable(null);
        }

        validateDeadline(dto.getDeadline(), projet);

        tache.setDescription(dto.getDescription());
        tache.setEtat(dto.getEtat());
        tache.setPriorite(dto.getPriorite());
        tache.setDeadline(dto.getDeadline());

        tache = tacheRepository.save(tache);
        return tacheConvertor.toDto(tache);
    }

    @Transactional
    public void delete(Long id) {
        if (!tacheRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tache introuvable avec id: " + id);
        }
        tacheRepository.deleteById(id);
    }

    @Transactional
    public TacheDTO addRessource(Long tacheId, Long ressourceId) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tache introuvable avec id: " + tacheId));
        Ressource ressource = ressourceRepository.findById(ressourceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ressource introuvable avec id: " + ressourceId));

        tache.getRessources().add(ressource);
        tache = tacheRepository.save(tache);
        return tacheConvertor.toDto(tache);
    }

    @Transactional
    public TacheDTO removeRessource(Long tacheId, Long ressourceId) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tache introuvable avec id: " + tacheId));

        tache.getRessources().removeIf(r -> r.getId().equals(ressourceId));
        tache = tacheRepository.save(tache);
        return tacheConvertor.toDto(tache);
    }

    public List<TacheDTO> findByEtat(String etat) {
        return tacheConvertor.toListDto(tacheRepository.findByEtat(etat));
    }

    public List<TacheDTO> findByPriorite(String priorite) {
        return tacheConvertor.toListDto(tacheRepository.findByPriorite(priorite));
    }

    // ===== Validation =====

    private void validateDeadline(LocalDate deadline, Projet projet) {
        if (deadline == null) return;
        if (projet.getDateDebut() != null && deadline.isBefore(projet.getDateDebut())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                "La deadline (" + deadline + ") ne peut pas etre avant la date de debut du projet (" + projet.getDateDebut() + ")");
        }
        if (projet.getDateFin() != null && deadline.isAfter(projet.getDateFin())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                "La deadline (" + deadline + ") ne peut pas depasser la date de fin du projet (" + projet.getDateFin() + ")");
        }
    }

    // ===== Construction entite depuis DTO (champs relationnels resolus ici) =====

    private Tache buildEntity(TacheDTO dto) {
        Projet projet = projetRepository.findById(dto.getProjetId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Projet introuvable avec id: " + dto.getProjetId()));

        Employe responsable = null;
        if (dto.getResponsableId() != null) {
            responsable = employeRepository.findById(dto.getResponsableId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Employe introuvable avec id: " + dto.getResponsableId()));
        }

        Tache tache = new Tache();
        tache.setProjet(projet);
        tache.setResponsable(responsable);
        tache.setDescription(dto.getDescription());
        tache.setEtat(dto.getEtat());
        tache.setPriorite(dto.getPriorite());
        tache.setDeadline(dto.getDeadline());
        return tache;
    }
}
