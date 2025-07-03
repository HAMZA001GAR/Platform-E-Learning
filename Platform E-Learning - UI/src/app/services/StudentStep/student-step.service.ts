import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudentStepService {

  private apiUrl = 'http://localhost:8090/student-steps/stepsStudents';
  private completeStepsUrl = 'http://localhost:8090/student-steps/progress';

  private baseUrl = 'http://localhost:8090/student-steps';


  constructor(private http: HttpClient) { }



  findCompletedStepsByParcoursId(parcoursId: number, studentId: number): Observable<any> {
    return this.http.get<any>(`${this.completeStepsUrl}/${parcoursId}?studentId=${studentId}`);
  }

  getStudentsInStep(stepId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${stepId}`);
  }

  deleteStudentStep(studentId: number, stepId: number, parcoursId: number): Observable<void> {
    const url = `${this.baseUrl}/delete-student-step/${studentId}/${stepId}/${parcoursId}`;
    return this.http.delete<void>(url);
  }
}
