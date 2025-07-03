import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Parcours } from '../../models/parcours';

@Injectable({
  providedIn: 'root'
})
export class ParcoursService {

  private apiUrl = 'http://localhost:8090/parcours';


  constructor(private http: HttpClient) { }

  getAllParcours(): Observable<Parcours[]> {

    return this.http.get<Array<Parcours>>(this.apiUrl);
  }

  getParcoursById(id: string): Observable<Parcours> {
    return this.http.get<Parcours>(`${this.apiUrl}/${id}`);
  }
  saveParcours(parcours: FormData,image: File): Observable<Parcours> {
    const formData: FormData = new FormData();
    formData.append('parcoursDTO', new Blob([JSON.stringify(parcours)], { type: 'application/json' }));
    formData.append('file', image);
    return this.http.post<Parcours>(`${this.apiUrl}/save`, formData)
  
  }
  deleteParcours(id: number): Observable<void> {
    console.log(`Sending delete request for parcours with id: ${id}`);
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }


}