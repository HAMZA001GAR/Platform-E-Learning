import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Formation } from '../../models/formation';

@Injectable({
  providedIn: 'root'
})
export class FormationService {
  private apiUrl = 'http://localhost:8090/formations';

  constructor(private http: HttpClient) { }

  saveFormation(formation: { formationsName: string, formationsDescription: string }, image: File): Observable<Formation> {
    const formData: FormData = new FormData();
    formData.append('formationDTO', new Blob([JSON.stringify(formation)], { type: 'application/json' }));
    formData.append('file', image);

    return this.http.post<Formation>(`${this.apiUrl}/save`, formData);
  }
  getFormations(): Observable<Formation[]> {
    return this.http.get<Formation[]>(`${this.apiUrl}/all`);
  }
  deleteFormation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
