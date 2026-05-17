import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tache } from '../models/tache.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class TacheService {
  private url = `${environment.apiUrl}/taches`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Tache[]> {
    return this.http.get<Tache[]>(this.url);
  }

  getByProjet(projetId: number): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.url}/projet/${projetId}`);
  }

  getById(id: number): Observable<Tache> {
    return this.http.get<Tache>(`${this.url}/${id}`);
  }

  create(tache: Tache): Observable<Tache> {
    return this.http.post<Tache>(this.url, tache);
  }

  update(id: number, tache: Tache): Observable<Tache> {
    return this.http.put<Tache>(`${this.url}/${id}`, tache);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
