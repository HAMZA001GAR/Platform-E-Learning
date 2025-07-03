import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';



@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {


  private registerUrl = 'http://localhost:8090/api/auth/register';
  private loginUrl = 'http://localhost:8090/api/auth';
  private parcoursUrl = 'http://localhost:8090/parcours';
  private formationsUrl = 'http://localhost:8090/formations/all';

  constructor(private http: HttpClient, private router: Router, private jwtHelper: JwtHelperService) { }

  register(user: any): Observable<any> {

    return this.http.post(this.registerUrl, user);
  }

  getParcoursList(): Observable<any[]> {
    console.log(this.http.get<any[]>(this.parcoursUrl));
    return this.http.get<any[]>(this.parcoursUrl);
  }

  getFormationsList(): Observable<any []> {
    return this.http.get<any[]>(this.formationsUrl);
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(`http://localhost:8090/api/auth/authenticate`, { email, password }).pipe(
      tap(response => {
        const userId = response.user.id;
        localStorage.setItem('userId', userId.toString());
        this.router.navigate([`/step-progress/${userId}`]);
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 409) {
      return throwError(() => new Error('Email already exists'));
    }
    return throwError(() => new Error('Something went wrong. Please try again later.'));
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('foramtionId');
    localStorage.removeItem('userId');
    this.router.navigate(['/signin']);
  }


  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRoles(): string | null {
    return localStorage.getItem('role');
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    return this.jwtHelper.isTokenExpired(token);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (token) {
      const isExpired = this.isTokenExpired();
      console.log("************")
      return !isExpired;
    }
    return false;
  }
}

