package com.gestion.projets.convertor;

import com.gestion.projets.dto.RessourceDTO;
import com.gestion.projets.entity.Ressource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RessourceConvertor {

    @Autowired // injection
    private ModelMapper mmapper;

    public RessourceDTO toDto(Ressource ressource) {
        return mmapper.map(ressource, RessourceDTO.class);
    }

    public Ressource fromDto(RessourceDTO dto) {
        return mmapper.map(dto, Ressource.class);
    }

    public List<RessourceDTO> toListDto(List<Ressource> listeR) {
        return listeR.stream()
                .map(r -> toDto(r))
                .collect(Collectors.toList());
    }
}
