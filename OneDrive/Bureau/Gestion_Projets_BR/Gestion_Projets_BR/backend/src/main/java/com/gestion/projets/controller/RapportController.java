package com.gestion.projets.controller;

import com.gestion.projets.dto.AvancementDTO;
import com.gestion.projets.dto.RapportFinancierDTO;
import com.gestion.projets.service.RapportFinancierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rapports")
@Tag(name = "Rapports", description = "Rapports financiers et suivi de l'avancement des projets")
public class RapportController {

    private final RapportFinancierService rapportFinancierService;

    public RapportController(RapportFinancierService rapportFinancierService) {
        this.rapportFinancierService = rapportFinancierService;
    }

    @GetMapping("/projet/{projetId}")
    @Operation(summary = "Rapport financier d'un projet (coûts, budget restant)")
    public ResponseEntity<RapportFinancierDTO> getRapportByProjet(@PathVariable Long projetId) {
        return ResponseEntity.ok(rapportFinancierService.getRapportByProjet(projetId));
    }

    @GetMapping("/couts")
    @Operation(summary = "Rapport financier global de tous les projets")
    public ResponseEntity<List<RapportFinancierDTO>> getRapportGlobal() {
        return ResponseEntity.ok(rapportFinancierService.getRapportGlobal());
    }

    @GetMapping("/avancement/{projetId}")
    @Operation(summary = "Avancement d'un projet spécifique")
    public ResponseEntity<AvancementDTO> getAvancementByProjet(@PathVariable Long projetId) {
        return ResponseEntity.ok(rapportFinancierService.getAvancementByProjet(projetId));
    }

    @GetMapping("/avancement")
    @Operation(summary = "Avancement global de tous les projets")
    public ResponseEntity<List<AvancementDTO>> getAvancementGlobal() {
        return ResponseEntity.ok(rapportFinancierService.getAvancementGlobal());
    }
}
