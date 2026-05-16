package com.gestion.projets.service;

import com.gestion.projets.dto.AvancementDTO;
import com.gestion.projets.dto.RapportFinancierDTO;
import com.gestion.projets.entity.Projet;
import com.gestion.projets.entity.Ressource;
import com.gestion.projets.repository.ProjetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RapportFinancierService {

    private final ProjetRepository projetRepository;

    public RapportFinancierService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    public RapportFinancierDTO getRapportByProjet(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Projet introuvable avec id: " + projetId));
        return buildRapport(projet);
    }

    public List<RapportFinancierDTO> getRapportGlobal() {
        return projetRepository.findAll().stream()
                .map(this::buildRapport)
                .collect(Collectors.toList());
    }

    public AvancementDTO getAvancementByProjet(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Projet introuvable avec id: " + projetId));
        return buildAvancement(projet);
    }

    public List<AvancementDTO> getAvancementGlobal() {
        return projetRepository.findAll().stream()
                .map(this::buildAvancement)
                .collect(Collectors.toList());
    }

    private AvancementDTO buildAvancement(Projet projet) {
        int total = projet.getTaches().size();
        long terminees = projet.getTaches().stream()
                .filter(t -> "TERMINEE".equals(t.getEtat())).count();
        long enCours = projet.getTaches().stream()
                .filter(t -> "EN_COURS".equals(t.getEtat())).count();
        long aFaire = projet.getTaches().stream()
                .filter(t -> "A_FAIRE".equals(t.getEtat())).count();

        double pourcentage = total > 0 ? (double) terminees / total * 100 : 0.0;

        AvancementDTO dto = new AvancementDTO();
        dto.setProjetId(projet.getId());
        dto.setProjetNom(projet.getNom());
        dto.setStatut(projet.getStatut());
        dto.setTotalTaches(total);
        dto.setTachesTerminees((int) terminees);
        dto.setTachesEnCours((int) enCours);
        dto.setTachesAFaire((int) aFaire);
        dto.setPourcentageAvancement(Math.round(pourcentage * 100.0) / 100.0);
        return dto;
    }

    private RapportFinancierDTO buildRapport(Projet projet) {
        Set<Ressource> toutesRessources = new HashSet<>(projet.getRessources());
        projet.getTaches().forEach(tache -> toutesRessources.addAll(tache.getRessources()));

        BigDecimal coutTotal = toutesRessources.stream()
                .map(Ressource::getCout)
                .filter(c -> c != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal budget = projet.getBudget() != null ? projet.getBudget() : BigDecimal.ZERO;
        BigDecimal budgetRestant = budget.subtract(coutTotal);

        Double pourcentage = budget.compareTo(BigDecimal.ZERO) > 0
                ? coutTotal.multiply(BigDecimal.valueOf(100))
                    .divide(budget, 2, RoundingMode.HALF_UP)
                    .doubleValue()
                : 0.0;

        List<RapportFinancierDTO.CoutRessourceDTO> detailCouts = toutesRessources.stream()
                .map(r -> {
                    RapportFinancierDTO.CoutRessourceDTO d = new RapportFinancierDTO.CoutRessourceDTO();
                    d.setRessourceNom(r.getNom());
                    d.setRessourceType(r.getType());
                    d.setCout(r.getCout());
                    return d;
                })
                .collect(Collectors.toList());

        RapportFinancierDTO dto = new RapportFinancierDTO();
        dto.setProjetId(projet.getId());
        dto.setProjetNom(projet.getNom());
        dto.setStatut(projet.getStatut());
        dto.setBudget(budget);
        dto.setCoutTotalRessources(coutTotal);
        dto.setBudgetRestant(budgetRestant);
        dto.setPourcentageUtilise(pourcentage);
        dto.setNombreTaches(projet.getTaches().size());
        dto.setNombreRessources(toutesRessources.size());
        dto.setDetailCouts(detailCouts);
        return dto;
    }
}
