import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employe } from '../models/employe.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class EmployeService {
  private url = `${environment.apiUrl}/employes`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Employe[]> {
    return this.http.get<Employe[]>(this.url);
  }

  getById(id: number): Observable<Employe> {
    return this.http.get<Employe>(`${this.url}/${id}`);
  }

  create(employe: Employe): Observable<Employe> {
    return this.http.post<Employe>(this.url, employe);
  }

  update(id: number, employe: Employe): Observable<Employe> {
    return this.http.put<Employe>(`${this.url}/${id}`, employe);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
