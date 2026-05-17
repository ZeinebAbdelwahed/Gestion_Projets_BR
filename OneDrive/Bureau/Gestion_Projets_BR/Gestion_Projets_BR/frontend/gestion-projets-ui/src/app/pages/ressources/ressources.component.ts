import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RessourceService } from '../../services/ressource.service';
import { Ressource } from '../../models/ressource.model';

@Component({
  selector: 'app-ressources',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ressources.component.html'
})
export class RessourcesComponent implements OnInit {
  ressources: Ressource[] = [];
  ressource: Ressource = { nom: '', type: '', cout: 0, disponibilite: true };
  editMode = false;
  showForm = false;
  errorMsg = '';

  readonly types = ['HUMAIN', 'MATERIEL', 'LOGICIEL', 'FINANCIER'];

  constructor(private ressourceService: RessourceService) {}

  ngOnInit() { this.load(); }

  load() {
    this.ressourceService.getAll().subscribe({
      next: data => this.ressources = data,
      error: () => this.errorMsg = 'Erreur lors du chargement.'
    });
  }

  openNew() {
    this.ressource = { nom: '', type: '', cout: 0, disponibilite: true };
    this.editMode = false;
    this.showForm = true;
  }

  openEdit(r: Ressource) {
    this.ressource = { ...r };
    this.editMode = true;
    this.showForm = true;
  }

  save() {
    if (this.editMode && this.ressource.id) {
      this.ressourceService.update(this.ressource.id, this.ressource).subscribe(() => { this.load(); this.showForm = false; });
    } else {
      this.ressourceService.create(this.ressource).subscribe(() => { this.load(); this.showForm = false; });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer cette ressource ?')) {
      this.ressourceService.delete(id).subscribe(() => this.load());
    }
  }
}
