package com.gestion.projets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TacheDTO {

    private Long id;

    @NotNull(message = "Le projet est obligatoire")
    private Long projetId;

    private String projetNom;
    private Long responsableId;
    private String responsableNom;

    @NotBlank(message = "La description de la tâche est obligatoire")
    @Size(min = 3, max = 255, message = "La description doit contenir entre 3 et 255 caractères")
    private String description;

    @NotBlank(message = "L'état est obligatoire")
    private String etat;

    @NotBlank(message = "La priorité est obligatoire")
    private String priorite;

    private LocalDate deadline;
    private Set<Long> ressourceIds;
}
