package com.gestion.projets.config;

import com.gestion.projets.entity.*;
import com.gestion.projets.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProjetRepository projetRepository;
    private final TacheRepository tacheRepository;
    private final RessourceRepository ressourceRepository;
    private final EmployeRepository employeRepository;

    public DataInitializer(ProjetRepository projetRepository, TacheRepository tacheRepository,
                           RessourceRepository ressourceRepository, EmployeRepository employeRepository) {
        this.projetRepository = projetRepository;
        this.tacheRepository = tacheRepository;
        this.ressourceRepository = ressourceRepository;
        this.employeRepository = employeRepository;
    }

    @Override
    public void run(String... args) {
        if (employeRepository.count() > 0) return;

        // ===== Employes =====
        Employe emp1 = new Employe();
        emp1.setNom("Ahmed Ben Ali"); emp1.setEmail("ahmed.benali@email.com");
        emp1.setRole("Chef de Projet"); emp1.setEquipe("Equipe A");
        emp1 = employeRepository.save(emp1);

        Employe emp2 = new Employe();
        emp2.setNom("Fatma Trabelsi"); emp2.setEmail("fatma.trabelsi@email.com");
        emp2.setRole("Developpeur Senior"); emp2.setEquipe("Equipe A");
        emp2 = employeRepository.save(emp2);

        Employe emp3 = new Employe();
        emp3.setNom("Mohamed Kharrat"); emp3.setEmail("mohamed.kharrat@email.com");
        emp3.setRole("Designer UX"); emp3.setEquipe("Equipe B");
        emp3 = employeRepository.save(emp3);

        Employe emp4 = new Employe();
        emp4.setNom("Sarra Mansour"); emp4.setEmail("sarra.mansour@email.com");
        emp4.setRole("Developpeur Junior"); emp4.setEquipe("Equipe B");
        emp4 = employeRepository.save(emp4);

        Employe emp5 = new Employe();
        emp5.setNom("Youssef Hamdi"); emp5.setEmail("youssef.hamdi@email.com");
        emp5.setRole("DevOps"); emp5.setEquipe("Equipe A");
        emp5 = employeRepository.save(emp5);

        // ===== Ressources =====
        Ressource res1 = new Ressource();
        res1.setNom("Serveur Cloud AWS"); res1.setType("Infrastructure");
        res1.setCout(new BigDecimal("1500.00")); res1.setDisponibilite(true);
        res1 = ressourceRepository.save(res1);

        Ressource res2 = new Ressource();
        res2.setNom("Licence IntelliJ IDEA"); res2.setType("Logiciel");
        res2.setCout(new BigDecimal("500.00")); res2.setDisponibilite(true);
        res2 = ressourceRepository.save(res2);

        Ressource res3 = new Ressource();
        res3.setNom("MacBook Pro M3"); res3.setType("Materiel");
        res3.setCout(new BigDecimal("3200.00")); res3.setDisponibilite(true);
        res3 = ressourceRepository.save(res3);

        Ressource res4 = new Ressource();
        res4.setNom("Base de donnees PostgreSQL"); res4.setType("Infrastructure");
        res4.setCout(new BigDecimal("800.00")); res4.setDisponibilite(true);
        res4 = ressourceRepository.save(res4);

        Ressource res5 = new Ressource();
        res5.setNom("Licence Figma Pro"); res5.setType("Logiciel");
        res5.setCout(new BigDecimal("150.00")); res5.setDisponibilite(false);
        res5 = ressourceRepository.save(res5);

        Ressource res6 = new Ressource();
        res6.setNom("Salle de reunion equipee"); res6.setType("Materiel");
        res6.setCout(new BigDecimal("200.00")); res6.setDisponibilite(true);
        res6 = ressourceRepository.save(res6);

        // ===== Projets =====
        Projet p1 = new Projet();
        p1.setNom("Plateforme E-Commerce");
        p1.setDateDebut(LocalDate.of(2026, 1, 15));
        p1.setDateFin(LocalDate.of(2026, 7, 30));
        p1.setBudget(new BigDecimal("50000.00"));
        p1.setStatut("EN_COURS");
        p1 = projetRepository.save(p1);

        Projet p2 = new Projet();
        p2.setNom("Application Mobile Sante");
        p2.setDateDebut(LocalDate.of(2026, 3, 1));
        p2.setDateFin(LocalDate.of(2026, 12, 31));
        p2.setBudget(new BigDecimal("75000.00"));
        p2.setStatut("EN_COURS");
        p2 = projetRepository.save(p2);

        Projet p3 = new Projet();
        p3.setNom("Systeme de Gestion RH");
        p3.setDateDebut(LocalDate.of(2025, 6, 1));
        p3.setDateFin(LocalDate.of(2026, 2, 28));
        p3.setBudget(new BigDecimal("30000.00"));
        p3.setStatut("TERMINE");
        p3 = projetRepository.save(p3);

        Projet p4 = new Projet();
        p4.setNom("Migration Cloud Infrastructure");
        p4.setDateDebut(LocalDate.of(2026, 5, 1));
        p4.setDateFin(LocalDate.of(2026, 11, 30));
        p4.setBudget(new BigDecimal("120000.00"));
        p4.setStatut("EN_ATTENTE");
        p4 = projetRepository.save(p4);

        // ===== Attribuer ressources aux projets =====
        p1.getRessources().add(res1);
        p1.getRessources().add(res2);
        p1.getRessources().add(res4);
        projetRepository.save(p1);

        p2.getRessources().add(res3);
        p2.getRessources().add(res5);
        projetRepository.save(p2);

        p3.getRessources().add(res2);
        p3.getRessources().add(res6);
        projetRepository.save(p3);

        // ===== Taches =====
        Tache t1 = new Tache();
        t1.setProjet(p1); t1.setResponsable(emp2);
        t1.setDescription("Developper le module de paiement en ligne");
        t1.setEtat("EN_COURS"); t1.setPriorite("HAUTE");
        t1.setDeadline(LocalDate.of(2026, 4, 30));
        t1 = tacheRepository.save(t1);

        Tache t2 = new Tache();
        t2.setProjet(p1); t2.setResponsable(emp3);
        t2.setDescription("Concevoir les maquettes de l'interface utilisateur");
        t2.setEtat("TERMINEE"); t2.setPriorite("HAUTE");
        t2.setDeadline(LocalDate.of(2026, 3, 15));
        tacheRepository.save(t2);

        Tache t3 = new Tache();
        t3.setProjet(p1); t3.setResponsable(emp4);
        t3.setDescription("Implementer le panier d'achat");
        t3.setEtat("A_FAIRE"); t3.setPriorite("MOYENNE");
        t3.setDeadline(LocalDate.of(2026, 5, 15));
        tacheRepository.save(t3);

        Tache t4 = new Tache();
        t4.setProjet(p2); t4.setResponsable(emp1);
        t4.setDescription("Definir l'architecture de l'application mobile");
        t4.setEtat("EN_COURS"); t4.setPriorite("HAUTE");
        t4.setDeadline(LocalDate.of(2026, 4, 15));
        t4 = tacheRepository.save(t4);

        Tache t5 = new Tache();
        t5.setProjet(p2); t5.setResponsable(emp4);
        t5.setDescription("Developper le module de suivi medical");
        t5.setEtat("A_FAIRE"); t5.setPriorite("MOYENNE");
        t5.setDeadline(LocalDate.of(2026, 6, 30));
        tacheRepository.save(t5);

        Tache t6 = new Tache();
        t6.setProjet(p3); t6.setResponsable(emp5);
        t6.setDescription("Deployer l'application en production");
        t6.setEtat("TERMINEE"); t6.setPriorite("HAUTE");
        t6.setDeadline(LocalDate.of(2026, 2, 15));
        tacheRepository.save(t6);

        // Attribuer ressources aux taches
        t1.getRessources().add(res4);
        tacheRepository.save(t1);

        t4.getRessources().add(res3);
        tacheRepository.save(t4);
    }
}
