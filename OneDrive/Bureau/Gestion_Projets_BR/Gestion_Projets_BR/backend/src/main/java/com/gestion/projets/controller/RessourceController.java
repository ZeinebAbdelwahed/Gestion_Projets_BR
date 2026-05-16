package com.gestion.projets.controller;

import com.gestion.projets.dto.RessourceDTO;
import com.gestion.projets.service.RessourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ressources")
@Tag(name = "Ressources", description = "Gestion des ressources : CRUD et disponibilité")
public class RessourceController {

    private final RessourceService ressourceService;

    public RessourceController(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les ressources")
    public ResponseEntity<List<RessourceDTO>> getAll() {
        return ResponseEntity.ok(ressourceService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une ressource par ID")
    public ResponseEntity<RessourceDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ressourceService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle ressource")
    public ResponseEntity<RessourceDTO> create(@Valid @RequestBody RessourceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ressourceService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une ressource existante")
    public ResponseEntity<RessourceDTO> update(@PathVariable Long id, @Valid @RequestBody RessourceDTO dto) {
        return ResponseEntity.ok(ressourceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une ressource")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ressourceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister uniquement les ressources disponibles")
    public ResponseEntity<List<RessourceDTO>> getDisponibles() {
        return ResponseEntity.ok(ressourceService.findByDisponibilite(true));
    }
}
