package com.gestion.projets.convertor;

import com.gestion.projets.dto.EmployeDTO;
import com.gestion.projets.entity.Employe;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeConvertor {

    @Autowired // injection
    private ModelMapper mmapper;

    public EmployeDTO toDto(Employe employe) {
        return mmapper.map(employe, EmployeDTO.class);
    }

    public Employe fromDto(EmployeDTO dto) {
        return mmapper.map(dto, Employe.class);
    }

    public List<EmployeDTO> toListDto(List<Employe> listeE) {
        return listeE.stream()
                .map(e -> toDto(e))
                .collect(Collectors.toList());
    }
}
