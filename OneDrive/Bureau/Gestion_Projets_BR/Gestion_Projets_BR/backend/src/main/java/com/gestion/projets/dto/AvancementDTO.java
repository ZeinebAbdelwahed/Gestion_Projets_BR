package com.gestion.projets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvancementDTO {

    private Long projetId;
    private String projetNom;
    private String statut;
    private int totalTaches;
    private int tachesTerminees;
    private int tachesEnCours;
    private int tachesAFaire;
    private double pourcentageAvancement;
}
