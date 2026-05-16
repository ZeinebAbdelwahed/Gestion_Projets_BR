package com.gestion.projets.service;

import com.gestion.projets.convertor.EmployeConvertor;
import com.gestion.projets.dto.EmployeDTO;
import com.gestion.projets.entity.Employe;
import com.gestion.projets.repository.EmployeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final EmployeConvertor employeConvertor;

    public EmployeService(EmployeRepository employeRepository, EmployeConvertor employeConvertor) {
        this.employeRepository = employeRepository;
        this.employeConvertor = employeConvertor;
    }

    public List<EmployeDTO> findAll() {
        return employeConvertor.toListDto(employeRepository.findAll());
    }

    public EmployeDTO findById(Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Employe introuvable avec id: " + id));
        return employeConvertor.toDto(employe);
    }

    public EmployeDTO create(EmployeDTO dto) {
        Employe employe = employeConvertor.fromDto(dto);
        employe = employeRepository.save(employe);
        return employeConvertor.toDto(employe);
    }

    public EmployeDTO update(Long id, EmployeDTO dto) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Employe introuvable avec id: " + id));

        employe.setNom(dto.getNom());
        employe.setEmail(dto.getEmail());
        employe.setRole(dto.getRole());
        employe.setEquipe(dto.getEquipe());

        employe = employeRepository.save(employe);
        return employeConvertor.toDto(employe);
    }

    public void delete(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employe introuvable avec id: " + id);
        }
        employeRepository.deleteById(id);
    }

    public List<EmployeDTO> findByEquipe(String equipe) {
        return employeConvertor.toListDto(employeRepository.findByEquipe(equipe));
    }

    public List<EmployeDTO> findByRole(String role) {
        return employeConvertor.toListDto(employeRepository.findByRole(role));
    }
}
