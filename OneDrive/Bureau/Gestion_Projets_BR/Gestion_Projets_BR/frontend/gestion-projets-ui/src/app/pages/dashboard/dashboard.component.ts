import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProjetService } from '../../services/projet.service';
import { TacheService } from '../../services/tache.service';
import { EmployeService } from '../../services/employe.service';
import { RessourceService } from '../../services/ressource.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  stats = { projets: 0, taches: 0, employes: 0, ressources: 0 };
  projetsRecents: any[] = [];
  tachesRecentes: any[] = [];

  constructor(
    private projetService: ProjetService,
    private tacheService: TacheService,
    private employeService: EmployeService,
    private ressourceService: RessourceService
  ) {}

  ngOnInit() {
    forkJoin({
      projets: this.projetService.getAll(),
      taches: this.tacheService.getAll(),
      employes: this.employeService.getAll(),
      ressources: this.ressourceService.getAll()
    }).subscribe(({ projets, taches, employes, ressources }) => {
      this.stats = {
        projets: projets.length,
        taches: taches.length,
        employes: employes.length,
        ressources: ressources.length
      };
      this.projetsRecents = projets.slice(0, 5);
      this.tachesRecentes = taches.slice(0, 5);
    });
  }

  statutClass(statut: string): string {
    const map: Record<string, string> = {
      EN_COURS: 'bg-primary', TERMINE: 'bg-success',
      EN_ATTENTE: 'bg-warning text-dark', ANNULE: 'bg-danger'
    };
    return map[statut] || 'bg-secondary';
  }

  etatClass(etat: string): string {
    const map: Record<string, string> = { A_FAIRE: 'bg-secondary', EN_COURS: 'bg-primary', TERMINE: 'bg-success' };
    return map[etat] || 'bg-light text-dark';
  }
}
