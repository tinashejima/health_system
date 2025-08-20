import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { LocalStorageService } from '../services/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements  CanActivate {

  constructor(private authService: LocalStorageService, private router: Router) {

  }
 
  canActivate(): boolean {
    return this.checkAuth();
  }

  private checkAuth(): boolean {
    try {
      if (this.authService.get('auth-key')) {
        return true;
      }
    } catch (e) {
      // On server-side rendering, localStorage is not available.
      // We'll treat this as not being authenticated.
    }
    
    this.router.navigate(['/login']);
    return false;
  }

};