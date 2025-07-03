import { Injectable } from '@angular/core';
import { Learning } from '../../models/learning';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { LearningPayload } from '../../models/LearningPayload';

@Injectable({
  providedIn: 'root'
})
export class LearningService {

  private apiUrl = 'http://localhost:8090/learnings/save';

  constructor(private http: HttpClient) { }

  DeleteLearningByStepId(StepsId: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8090/learnings/steps/${StepsId}`);
   }

  saveLearning(learning: LearningPayload): Observable<LearningPayload> {
    return this.http.post<LearningPayload>(this.apiUrl, learning);
  }


}