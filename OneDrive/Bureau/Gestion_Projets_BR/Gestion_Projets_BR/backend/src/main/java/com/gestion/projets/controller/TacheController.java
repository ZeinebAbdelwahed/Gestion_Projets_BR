package com.gestion.projets.controller;

import com.gestion.projets.dto.TacheDTO;
import com.gestion.projets.service.TacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@Tag(name = "Tâches", description = "Gestion des tâches : création, attribution, suivi et ressources")
public class TacheController {

    private final TacheService tacheService;

    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les tâches")
    public ResponseEntity<List<TacheDTO>> getAll() {
        return ResponseEntity.ok(tacheService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une tâche par ID")
    public ResponseEntity<TacheDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tacheService.findById(id));
    }

    @GetMapping("/projet/{projetId}")
    @Operation(summary = "Lister les tâches d'un projet")
    public ResponseEntity<List<TacheDTO>> getByProjet(@PathVariable Long projetId) {
        return ResponseEntity.ok(tacheService.findByProjetId(projetId));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle tâche")
    public ResponseEntity<TacheDTO> create(@Valid @RequestBody TacheDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tacheService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une tâche existante")
    public ResponseEntity<TacheDTO> update(@PathVariable Long id, @Valid @RequestBody TacheDTO dto) {
        return ResponseEntity.ok(tacheService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tacheService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tacheId}/ressources/{ressourceId}")
    @Operation(summary = "Attribuer une ressource à une tâche")
    public ResponseEntity<TacheDTO> addRessource(@PathVariable Long tacheId, @PathVariable Long ressourceId) {
        return ResponseEntity.ok(tacheService.addRessource(tacheId, ressourceId));
    }

    @DeleteMapping("/{tacheId}/ressources/{ressourceId}")
    @Operation(summary = "Retirer une ressource d'une tâche")
    public ResponseEntity<TacheDTO> removeRessource(@PathVariable Long tacheId, @PathVariable Long ressourceId) {
        return ResponseEntity.ok(tacheService.removeRessource(tacheId, ressourceId));
    }
}
