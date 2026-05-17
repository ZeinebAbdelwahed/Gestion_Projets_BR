import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RapportService } from '../../services/rapport.service';
import { ProjetService } from '../../services/projet.service';
import { RapportFinancierDTO, AvancementDTO } from '../../models/rapport.model';
import { Projet } from '../../models/projet.model';

@Component({
  selector: 'app-rapports',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './rapports.component.html'
})
export class RapportsComponent implements OnInit {
  projets: Projet[] = [];
  selectedProjetId: number | null = null;
  rapportFinancier: RapportFinancierDTO | null = null;
  avancement: AvancementDTO | null = null;
  errorMsg = '';
  loading = false;

  constructor(
    private rapportService: RapportService,
    private projetService: ProjetService
  ) {}

  ngOnInit() {
    this.projetService.getAll().subscribe({ next: d => this.projets = d });
  }

  charger() {
    if (!this.selectedProjetId) return;
    this.loading = true;
    this.rapportFinancier = null;
    this.avancement = null;
    this.errorMsg = '';

    this.rapportService.getRapportFinancier(this.selectedProjetId).subscribe({
      next: d => { this.rapportFinancier = d; this.loading = false; },
      error: () => { this.errorMsg = 'Erreur chargement rapport financier.'; this.loading = false; }
    });

    this.rapportService.getAvancement(this.selectedProjetId).subscribe({
      next: d => this.avancement = d,
      error: () => {}
    });
  }
}
