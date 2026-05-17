import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TacheService } from '../../services/tache.service';
import { ProjetService } from '../../services/projet.service';
import { EmployeService } from '../../services/employe.service';
import { Tache } from '../../models/tache.model';
import { Projet } from '../../models/projet.model';
import { Employe } from '../../models/employe.model';

@Component({
  selector: 'app-taches',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './taches.component.html'
})
export class TachesComponent implements OnInit {
  taches: Tache[] = [];
  projets: Projet[] = [];
  employes: Employe[] = [];
  tache: Tache = { projetId: 0, description: '', etat: 'A_FAIRE', priorite: 'MOYENNE' };
  editMode = false;
  showForm = false;
  errorMsg = '';

  readonly etats = ['A_FAIRE', 'EN_COURS', 'TERMINE'];
  readonly priorites = ['BASSE', 'MOYENNE', 'HAUTE', 'CRITIQUE'];

  constructor(
    private tacheService: TacheService,
    private projetService: ProjetService,
    private employeService: EmployeService
  ) {}

  ngOnInit() {
    this.tacheService.getAll().subscribe({ next: d => this.taches = d, error: () => this.errorMsg = 'Erreur chargement tâches.' });
    this.projetService.getAll().subscribe({ next: d => this.projets = d });
    this.employeService.getAll().subscribe({ next: d => this.employes = d });
  }

  openNew() {
    this.tache = { projetId: 0, description: '', etat: 'A_FAIRE', priorite: 'MOYENNE' };
    this.editMode = false;
    this.showForm = true;
  }

  openEdit(t: Tache) {
    this.tache = { ...t };
    this.editMode = true;
    this.showForm = true;
  }

  save() {
    if (this.editMode && this.tache.id) {
      this.tacheService.update(this.tache.id, this.tache).subscribe(() => {
        this.tacheService.getAll().subscribe(d => this.taches = d);
        this.showForm = false;
      });
    } else {
      this.tacheService.create(this.tache).subscribe(() => {
        this.tacheService.getAll().subscribe(d => this.taches = d);
        this.showForm = false;
      });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer cette tâche ?')) {
      this.tacheService.delete(id).subscribe(() => this.tacheService.getAll().subscribe(d => this.taches = d));
    }
  }

  etatClass(etat: string): string {
    const map: Record<string, string> = { A_FAIRE: 'bg-secondary', EN_COURS: 'bg-primary', TERMINE: 'bg-success' };
    return map[etat] || 'bg-light text-dark';
  }

  prioriteClass(p: string): string {
    const map: Record<string, string> = { BASSE: 'bg-success', MOYENNE: 'bg-info text-dark', HAUTE: 'bg-warning text-dark', CRITIQUE: 'bg-danger' };
    return map[p] || 'bg-light text-dark';
  }
}
