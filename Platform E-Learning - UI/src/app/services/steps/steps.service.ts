import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, forkJoin, map } from 'rxjs';
import { Step } from '../../models/step';
import { Learning } from '../../models/learning';
import { Doing } from '../../models/doing';
import { StepPayload } from '../../models/StepPayload';
import { PaginatedStepsResponse } from '../../models/PaginatedStepsResponse';

@Injectable({
  providedIn: 'root'
})
export class StepsService {


  private apiUrl = 'http://localhost:8090/steps';
  private learningUrl = 'http://localhost:8090';
  private doingUrl = 'http://localhost:8090';
  private stepsCountUrl = 'http://localhost:8090/student-steps/step-counts';
  private usersCountUrl = 'http://localhost:8090/users/by-parcours';
  private updatestep = "http://localhost:8090/steps/stepDoingLearning";


  constructor(private http: HttpClient) { }

  getCountUsersByParcoursId(parcoursId: number): Observable<number> {
    const url = `${this.usersCountUrl}/${parcoursId}`;
    return this.http.get<any[]>(url).pipe(
      map(users => users.length)
    );
  }

  DeleteStepsById(StepId: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8090/steps/${StepId}`);
  }

  saveStep(step: StepPayload, imageFile: File): Observable<StepPayload> {
    const formData: FormData = new FormData();
    formData.append('stepDTO', new Blob([JSON.stringify(step)], { type: 'application/json' }));
    formData.append('image', imageFile);

    return this.http.post<StepPayload>(this.apiUrl, formData);
  }

  getStepsCount(): Observable<any> {
    return this.http.get<any>(this.stepsCountUrl);
  }

  getStepsByParcoursId(parcoursId: number, page: number, size: number): Observable<PaginatedStepsResponse> {
    return this.http.get<PaginatedStepsResponse>(`http://localhost:8090/steps/parcours/${parcoursId}?page=${page}&size=${size}`);
  }
  // New method to get step details by ID
  getStepWithLearningAndDoing(stepId: number): Observable<any> {
    const step$ = this.http.get<Step>(`${this.apiUrl}/${stepId}`);
    const learning$ = this.http.get<Learning[]>(`${this.learningUrl}/learnings?stepsId=${stepId}`);
    const doing$ = this.http.get<Doing[]>(`${this.doingUrl}/doings?stepsId=${stepId}`);

    return forkJoin({ step: step$, learning: learning$, doing: doing$ });
  }

  updateStepWithLearningAndDoing(stepId: number, updatedData: any, imageFile?: File): Observable<any> {

    const formData = new FormData();
    formData.append('stepsDoingLeranings', new Blob([JSON.stringify(updatedData)], { type: 'application/json' }));

    if (imageFile) {
      formData.append('image', imageFile);
    }
    return this.http.put<any>(`${this.updatestep}/${stepId}`, formData);
  }

}
