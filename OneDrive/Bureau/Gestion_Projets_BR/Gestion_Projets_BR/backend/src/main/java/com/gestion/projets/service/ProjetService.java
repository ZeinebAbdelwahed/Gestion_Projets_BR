package com.gestion.projets.service;

import com.gestion.projets.convertor.ProjetConvertor;
import com.gestion.projets.dto.ProjetDTO;
import com.gestion.projets.entity.Projet;
import com.gestion.projets.entity.Ressource;
import com.gestion.projets.repository.ProjetRepository;
import com.gestion.projets.repository.RessourceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjetService {

    private final ProjetRepository projetRepository;
    private final RessourceRepository ressourceRepository;
    private final ProjetConvertor projetConvertor;

    public ProjetService(ProjetRepository projetRepository, RessourceRepository ressourceRepository,
                         ProjetConvertor projetConvertor) {
        this.projetRepository = projetRepository;
        this.ressourceRepository = ressourceRepository;
        this.projetConvertor = projetConvertor;
    }

    public List<ProjetDTO> findAll() {
        return projetConvertor.toListDto(projetRepository.findAll());
    }

    public ProjetDTO findById(Long id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Projet introuvable avec id: " + id));
        return projetConvertor.toDto(projet);
    }

    public ProjetDTO create(ProjetDTO dto) {
        Projet projet = projetConvertor.fromDto(dto);
        projet = projetRepository.save(projet);
        return projetConvertor.toDto(projet);
    }

    public ProjetDTO update(Long id, ProjetDTO dto) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Projet introuvable avec id: " + id));

        projet.setNom(dto.getNom());
        projet.setDateDebut(dto.getDateDebut());
        projet.setDateFin(dto.getDateFin());
        projet.setBudget(dto.getBudget());
        projet.setStatut(dto.getStatut());

        projet = projetRepository.save(projet);
        return projetConvertor.toDto(projet);
    }

    public void delete(Long id) {
        if (!projetRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Projet introuvable avec id: " + id);
        }
        projetRepository.deleteById(id);
    }

    public ProjetDTO addRessource(Long projetId, Long ressourceId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Projet introuvable avec id: " + projetId));
        Ressource ressource = ressourceRepository.findById(ressourceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ressource introuvable avec id: " + ressourceId));

        if (projet.getRessources().contains(ressource)) {
            return projetConvertor.toDto(projet);
        }

        // valider que l'ajout ne depasse pas le budget
        if (projet.getBudget() != null && ressource.getCout() != null) {
            BigDecimal coutActuel = projet.getRessources().stream()
                    .map(Ressource::getCout)
                    .filter(c -> c != null)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal coutApresAjout = coutActuel.add(ressource.getCout());
            if (coutApresAjout.compareTo(projet.getBudget()) > 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                        "Le budget du projet '" + projet.getNom() + "' serait depasse. Budget: "
                        + projet.getBudget() + ", Cout apres ajout: " + coutApresAjout);
            }
        }

        projet.getRessources().add(ressource);
        projet = projetRepository.save(projet);
        return projetConvertor.toDto(projet);
    }

    public ProjetDTO removeRessource(Long projetId, Long ressourceId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Projet introuvable avec id: " + projetId));

        projet.getRessources().removeIf(r -> r.getId().equals(ressourceId));
        projet = projetRepository.save(projet);
        return projetConvertor.toDto(projet);
    }

    public List<ProjetDTO> findByStatut(String statut) {
        return projetConvertor.toListDto(projetRepository.findByStatut(statut));
    }
}
