import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface StepWithStatus {
  id: number;
  title: string;
  durationInMinutes: number;
  completed: boolean;
  status: boolean;
}
export interface Step {
  id: number;
  title: string;
  category: string;
  durationInMinutes: number;
  completed: boolean;
  startTime: string | null;
  endTime: string | null;
  status: boolean;
}
export interface StepWithStatus {
  id: number;
  title: string;
  durationInMinutes: number;
  completed: boolean;
  imageUrl: String;
  category: string; // Add this
  startTime: string | null; // Add this
  endTime: string | null; // Add this
}

export interface Student {
  id: number;
  fullName: string;
  parcours: {
    id: number;
    formationName: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private apiUrl = 'http://localhost:8090';

  constructor(private http: HttpClient) {}

  getStepsWithCompletionStatus(studentId: number, parcoursId: number): Observable<StepWithStatus[]> {
    return this.http.get<StepWithStatus[]>(`${this.apiUrl}/student-steps/steps-status/${studentId}/${parcoursId}`);
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
  
  getParcoursByFormationsId(formationsId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/parcours/formations/${formationsId}`);
  }
}