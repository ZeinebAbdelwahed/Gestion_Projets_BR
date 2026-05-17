import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Projet } from '../models/projet.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ProjetService {
  private url = `${environment.apiUrl}/projets`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Projet[]> {
    return this.http.get<Projet[]>(this.url);
  }

  getById(id: number): Observable<Projet> {
    return this.http.get<Projet>(`${this.url}/${id}`);
  }

  create(projet: Projet): Observable<Projet> {
    return this.http.post<Projet>(this.url, projet);
  }

  update(id: number, projet: Projet): Observable<Projet> {
    return this.http.put<Projet>(`${this.url}/${id}`, projet);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  addRessource(projetId: number, ressourceId: number): Observable<Projet> {
    return this.http.post<Projet>(`${this.url}/${projetId}/ressources/${ressourceId}`, {});
  }

  removeRessource(projetId: number, ressourceId: number): Observable<Projet> {
    return this.http.delete<Projet>(`${this.url}/${projetId}/ressources/${ressourceId}`);
  }
}
