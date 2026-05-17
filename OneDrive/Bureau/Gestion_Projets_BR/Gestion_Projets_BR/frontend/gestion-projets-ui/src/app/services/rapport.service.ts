import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RapportFinancierDTO, AvancementDTO } from '../models/rapport.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class RapportService {
  private url = `${environment.apiUrl}/rapports`;

  constructor(private http: HttpClient) {}

  getRapportFinancier(projetId: number): Observable<RapportFinancierDTO> {
    return this.http.get<RapportFinancierDTO>(`${this.url}/projet/${projetId}`);
  }

  getAllRapports(): Observable<RapportFinancierDTO[]> {
    return this.http.get<RapportFinancierDTO[]>(`${this.url}/couts`);
  }

  getAvancement(projetId: number): Observable<AvancementDTO> {
    return this.http.get<AvancementDTO>(`${this.url}/avancement/${projetId}`);
  }

  getAllAvancements(): Observable<AvancementDTO[]> {
    return this.http.get<AvancementDTO[]>(`${this.url}/avancement`);
  }
}
