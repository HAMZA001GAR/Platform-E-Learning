import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from '../../models/student';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  private apiUrl = 'http://localhost:8090/users/by-parcours';
  private StudentapiUrl = 'http://localhost:8090/students';

  constructor(private http: HttpClient) { }

 
  getStudentById(id: number): Observable<Student> {
    const url = `${this.StudentapiUrl}/${id}`;
    return this.http.get<Student>(url);
  }

  getUsersByParcoursId(parcoursId: number): Observable<any[]> {
    const url = `${this.apiUrl}/${parcoursId}`;
    return this.http.get<any[]>(url);
  }
}
