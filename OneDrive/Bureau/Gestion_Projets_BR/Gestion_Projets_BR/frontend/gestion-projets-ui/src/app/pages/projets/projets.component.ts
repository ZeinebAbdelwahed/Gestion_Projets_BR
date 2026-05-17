import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProjetService } from '../../services/projet.service';
import { Projet } from '../../models/projet.model';

@Component({
  selector: 'app-projets',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './projets.component.html'
})
export class ProjetsComponent implements OnInit {
  projets: Projet[] = [];
  projet: Projet = { nom: '', dateDebut: '', dateFin: '', budget: 0, statut: 'EN_COURS' };
  editMode = false;
  showForm = false;
  errorMsg = '';

  readonly statuts = ['EN_COURS', 'TERMINE', 'EN_ATTENTE', 'ANNULE'];

  constructor(private projetService: ProjetService) {}

  ngOnInit() { this.load(); }

  load() {
    this.projetService.getAll().subscribe({
      next: data => this.projets = data,
      error: () => this.errorMsg = 'Erreur lors du chargement.'
    });
  }

  openNew() {
    this.projet = { nom: '', dateDebut: '', dateFin: '', budget: 0, statut: 'EN_COURS' };
    this.editMode = false;
    this.showForm = true;
  }

  openEdit(p: Projet) {
    this.projet = { ...p };
    this.editMode = true;
    this.showForm = true;
  }

  save() {
    if (this.editMode && this.projet.id) {
      this.projetService.update(this.projet.id, this.projet).subscribe(() => { this.load(); this.showForm = false; });
    } else {
      this.projetService.create(this.projet).subscribe(() => { this.load(); this.showForm = false; });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer ce projet ?')) {
      this.projetService.delete(id).subscribe(() => this.load());
    }
  }

  statutClass(statut: string): string {
    const map: Record<string, string> = {
      EN_COURS: 'bg-primary', TERMINE: 'bg-success',
      EN_ATTENTE: 'bg-warning text-dark', ANNULE: 'bg-danger'
    };
    return map[statut] || 'bg-secondary';
  }
}
