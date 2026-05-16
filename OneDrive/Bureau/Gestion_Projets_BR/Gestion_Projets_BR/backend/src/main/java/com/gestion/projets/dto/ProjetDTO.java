package com.gestion.projets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetDTO {

    private Long id;

    @NotBlank(message = "Le nom du projet est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @NotNull(message = "Le budget est obligatoire")
    @Positive(message = "Le budget doit être un montant positif")
    private BigDecimal budget;

    @NotBlank(message = "Le statut est obligatoire")
    private String statut;

    private Set<Long> ressourceIds;
    private BigDecimal coutTotal;
    private BigDecimal budgetRestant;
}
