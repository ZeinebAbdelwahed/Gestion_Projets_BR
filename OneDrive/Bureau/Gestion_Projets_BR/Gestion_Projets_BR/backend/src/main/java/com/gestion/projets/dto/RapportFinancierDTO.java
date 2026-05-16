package com.gestion.projets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapportFinancierDTO {

    private Long projetId;
    private String projetNom;
    private String statut;
    private BigDecimal budget;
    private BigDecimal coutTotalRessources;
    private BigDecimal budgetRestant;
    private Double pourcentageUtilise;
    private int nombreTaches;
    private int nombreRessources;
    private List<CoutRessourceDTO> detailCouts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoutRessourceDTO {
        private String ressourceNom;
        private String ressourceType;
        private BigDecimal cout;
    }
}
