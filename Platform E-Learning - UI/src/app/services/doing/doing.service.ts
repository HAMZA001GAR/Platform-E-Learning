import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Doing } from '../../models/doing';
import { Observable } from 'rxjs';
import { DoingPayload } from '../../models/DoingPayload';

@Injectable({
  providedIn: 'root'
})
export class DoingService {
  private apiUrl = 'http://localhost:8090/doings/save';

  constructor(private http: HttpClient) { }

  DeleteDoingByStepId(StepsId: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8090/doings/steps/${StepsId}`);
  }

  saveDoing(doing: DoingPayload): Observable<DoingPayload> {
    return this.http.post<DoingPayload>(this.apiUrl, doing);
  }
}