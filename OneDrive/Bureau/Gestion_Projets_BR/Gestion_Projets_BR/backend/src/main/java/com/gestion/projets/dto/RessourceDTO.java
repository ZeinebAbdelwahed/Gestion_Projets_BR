package com.gestion.projets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RessourceDTO {

    private Long id;

    @NotBlank(message = "Le nom de la ressource est obligatoire")
    private String nom;

    @NotBlank(message = "Le type de la ressource est obligatoire")
    private String type;

    @NotNull(message = "Le coût est obligatoire")
    @Positive(message = "Le coût doit être un montant positif")
    private BigDecimal cout;

    @NotNull(message = "La disponibilité est obligatoire")
    private Boolean disponibilite;
}
