import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthServiceService } from '../services/authentication/auth-service.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {


    constructor(private authServiceService: AuthServiceService) { }


    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = localStorage.getItem('token');
        const isApiUrl = request.url.startsWith('http://localhost:8090');
        if (token && isApiUrl) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            });
        }
        if (token && isApiUrl) {
            const isExpired = this.authServiceService.isTokenExpired();
            if (isExpired) {
                this.authServiceService.logout();
                return next.handle(request);
            }
        }

        return next.handle(request);
    }
}


