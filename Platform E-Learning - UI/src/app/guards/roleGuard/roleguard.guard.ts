import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthServiceService } from '../../services/authentication/auth-service.service';


@Injectable({
  providedIn: 'root'
})
export class roleguardGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthServiceService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    const expectedRoles = (route.data as { roles: string[] }).roles;
    const userRoles = this.authService.getRoles();
    console.log(userRoles);
    const hasRole = expectedRoles.find(role => userRoles?.includes(role));
    if (hasRole) {
     
      return true;
    } else {
      this.router.navigate(['/signin']);
      return false;
    }
  }
}
