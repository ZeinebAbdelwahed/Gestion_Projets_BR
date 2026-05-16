package com.gestion.projets.convertor;

import com.gestion.projets.dto.TacheDTO;
import com.gestion.projets.entity.Ressource;
import com.gestion.projets.entity.Tache;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TacheConvertor {

    @Autowired // injection
    private ModelMapper mmapper;

    public TacheDTO toDto(Tache tache) {
        // mapper les champs simples : id, description, etat, priorite, deadline
        TacheDTO dto = mmapper.map(tache, TacheDTO.class);

        // setter les champs relationnels manuellement (comme nomGr du prof)
        dto.setProjetId(tache.getProjet().getId());
        dto.setProjetNom(tache.getProjet().getNom());
        dto.setResponsableId(tache.getResponsable() != null ? tache.getResponsable().getId() : null);
        dto.setResponsableNom(tache.getResponsable() != null ? tache.getResponsable().getNom() : null);
        dto.setRessourceIds(tache.getRessources().stream()
                .map(Ressource::getId)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Tache fromDto(TacheDTO dto) {
        // mapper les champs simples seulement : id, description, etat, priorite, deadline
        // projet et responsable seront resolus dans le service via leurs IDs
        return mmapper.map(dto, Tache.class);
    }

    public List<TacheDTO> toListDto(List<Tache> listeT) {
        return listeT.stream()
                .map(t -> toDto(t))
                .collect(Collectors.toList());
    }
}
