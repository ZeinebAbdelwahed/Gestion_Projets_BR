package com.gestion.projets.controller;

import com.gestion.projets.dto.ProjetDTO;
import com.gestion.projets.service.ProjetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@Tag(name = "Projets", description = "Gestion des projets : CRUD, budget et ressources")
public class ProjetController {

    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les projets")
    public ResponseEntity<List<ProjetDTO>> getAll() {
        return ResponseEntity.ok(projetService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un projet par ID")
    public ResponseEntity<ProjetDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projetService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau projet")
    public ResponseEntity<ProjetDTO> create(@Valid @RequestBody ProjetDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projetService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un projet existant")
    public ResponseEntity<ProjetDTO> update(@PathVariable Long id, @Valid @RequestBody ProjetDTO dto) {
        return ResponseEntity.ok(projetService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un projet")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projetId}/ressources/{ressourceId}")
    @Operation(summary = "Attribuer une ressource à un projet")
    public ResponseEntity<ProjetDTO> addRessource(@PathVariable Long projetId, @PathVariable Long ressourceId) {
        return ResponseEntity.ok(projetService.addRessource(projetId, ressourceId));
    }

    @DeleteMapping("/{projetId}/ressources/{ressourceId}") // requete DELETE pour retirer une ressource d'un projet
    public ResponseEntity<ProjetDTO> removeRessource(@PathVariable Long projetId, @PathVariable Long ressourceId) {
        return ResponseEntity.ok(projetService.removeRessource(projetId, ressourceId));
    }

    @GetMapping("/statut/{statut}") // requete GET pour filtrer les projets par statut
    public ResponseEntity<List<ProjetDTO>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(projetService.findByStatut(statut));
    }
}
