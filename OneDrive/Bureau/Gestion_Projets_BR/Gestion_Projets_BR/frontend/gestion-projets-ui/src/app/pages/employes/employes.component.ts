import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeService } from '../../services/employe.service';
import { Employe } from '../../models/employe.model';

@Component({
  selector: 'app-employes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employes.component.html'
})
export class EmployesComponent implements OnInit {
  employes: Employe[] = [];
  employe: Employe = { nom: '', email: '', role: '', equipe: '' };
  editMode = false;
  showForm = false;
  errorMsg = '';

  constructor(private employeService: EmployeService) {}

  ngOnInit() { this.load(); }

  load() {
    this.employeService.getAll().subscribe({
      next: data => this.employes = data,
      error: () => this.errorMsg = 'Erreur lors du chargement.'
    });
  }

  openNew() {
    this.employe = { nom: '', email: '', role: '', equipe: '' };
    this.editMode = false;
    this.showForm = true;
  }

  openEdit(e: Employe) {
    this.employe = { ...e };
    this.editMode = true;
    this.showForm = true;
  }

  save() {
    if (this.editMode && this.employe.id) {
      this.employeService.update(this.employe.id, this.employe).subscribe(() => { this.load(); this.showForm = false; });
    } else {
      this.employeService.create(this.employe).subscribe(() => { this.load(); this.showForm = false; });
    }
  }

  delete(id: number) {
    if (confirm('Supprimer cet employé ?')) {
      this.employeService.delete(id).subscribe(() => this.load());
    }
  }
}
