package com.gestion.projets.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Employe responsable;

    private String description;

    private String etat;

    private String priorite;

    private LocalDate deadline;

    @ManyToMany
    @JoinTable(
        name = "tache_ressource",
        joinColumns = @JoinColumn(name = "tache_id"),
        inverseJoinColumns = @JoinColumn(name = "ressource_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ressource> ressources = new HashSet<>();
}
