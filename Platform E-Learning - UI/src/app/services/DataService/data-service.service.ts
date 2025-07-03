import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Step } from '../../models/step';
import { Student } from '../../models/student';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private apiUrl = 'http://localhost:8090/';

  constructor(private http: HttpClient) {}

  getStepsWithCompletionStatus(studentId: number, parcoursId: number): Observable<Step[]> {
    return this.http.get<Step[]>(`${this.apiUrl}/student-steps/steps-status/${studentId}/${parcoursId}`);
  }

  startStep(studentId: number, stepId: number): Observable<Step> {
    const params = new HttpParams()
      .set('studentId', studentId.toString())
      .set('stepId', stepId.toString());

    return this.http.post<Step>(`${this.apiUrl}/student-steps/start`, null, { params });
  }

  completeStep(studentId: number, stepId: number, taskUrl: string): Observable<Step> {
    const params = new HttpParams()
      .set('studentId', studentId.toString())
      .set('stepId', stepId.toString())
      .set('taskUrl', taskUrl);

    return this.http.post<Step>(`${this.apiUrl}/student-steps/complete`, null, { params });
  }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.apiUrl}/students`);
  }
  
}