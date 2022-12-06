import { LoginService } from './../services/login.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

    const requiredRoles = next.data['requiredRoles'];
    if (!this.loginService.getIsLogged()) {
      this.router.navigate(['/']);
      return false;
    }
    const realRol = this.loginService.getIsAdmin() ? 'meetup_admin' : 'meetup_user';
    if (requiredRoles.indexOf(realRol) === -1) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }

}
