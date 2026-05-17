import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ressource } from '../models/ressource.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class RessourceService {
  private url = `${environment.apiUrl}/ressources`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Ressource[]> {
    return this.http.get<Ressource[]>(this.url);
  }

  getById(id: number): Observable<Ressource> {
    return this.http.get<Ressource>(`${this.url}/${id}`);
  }

  create(ressource: Ressource): Observable<Ressource> {
    return this.http.post<Ressource>(this.url, ressource);
  }

  update(id: number, ressource: Ressource): Observable<Ressource> {
    return this.http.put<Ressource>(`${this.url}/${id}`, ressource);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
