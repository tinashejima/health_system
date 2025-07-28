import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth'; // Base URL
  private isAuthenticated = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private router: Router) {
    // Check initial auth state from localStorage
    this.isAuthenticated.next(!!this.getToken());
  }

  // Registration method
  register(userData: {
    username: string,
    email: string,
    password: string
    // Add any other required fields
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData).pipe(
      tap((response: any) => {
        // Optional: automatically login after registration
        if (response.token) {
          this.handleAuthentication(response);
        }
      })
    );
  }

  // Existing login method (updated to use full URL)
  login(credentials: { email: string, password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
      tap((response: any) => this.handleAuthentication(response))
    );
  }

  private handleAuthentication(response: any): void {
    if (response.token) {
      localStorage.setItem('auth_token', response.token);
      this.isAuthenticated.next(true);
    }
  }

  // Other existing methods...
  getAuthStatus(): Observable<boolean> {
    return this.isAuthenticated.asObservable();
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  logout(): void {
    localStorage.removeItem('auth_token');
    this.isAuthenticated.next(false);
    this.router.navigate(['/login']);
  }
}