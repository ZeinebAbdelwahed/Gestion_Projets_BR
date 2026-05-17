package com.gestion.projets.convertor;

import com.gestion.projets.dto.ProjetDTO;
import com.gestion.projets.entity.Projet;
import com.gestion.projets.entity.Ressource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjetConvertor {

    @Autowired // injection
    private ModelMapper mmapper;

    public ProjetDTO toDto(Projet projet) {
        // mapper les champs simples : id, nom, dateDebut, dateFin, budget, statut
        ProjetDTO dto = mmapper.map(projet, ProjetDTO.class);

        // union : ressources du projet + ressources de toutes ses taches
        Set<Ressource> toutesRessources = new HashSet<>(projet.getRessources());
        projet.getTaches().forEach(tache -> toutesRessources.addAll(tache.getRessources()));

        // calculer le cout total reel (toutes ressources confondues)
        BigDecimal coutTotal = toutesRessources.stream()
                .map(Ressource::getCout)
                .filter(c -> c != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // setter les champs calcules manuellement
        dto.setRessourceIds(projet.getRessources().stream()
                .map(Ressource::getId)
                .collect(Collectors.toSet()));
        dto.setCoutTotal(coutTotal);
        dto.setBudgetRestant(projet.getBudget() != null
                ? projet.getBudget().subtract(coutTotal)
                : BigDecimal.ZERO);

        return dto;
    }

    public Projet fromDto(ProjetDTO dto) {
        // mapper les champs simples seulement : id, nom, dateDebut, dateFin, budget, statut
        return mmapper.map(dto, Projet.class);
    }

    public List<ProjetDTO> toListDto(List<Projet> listeP) {
        return listeP.stream()
                .map(p -> toDto(p))
                .collect(Collectors.toList());
    }
}
