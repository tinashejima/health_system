import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


interface LoginPayload {
  username: string;
  password: string;
}


interface LoginResponse {
  message: string;
  username: string;
  
}

@Injectable({
  providedIn: 'root' 
})
export class AuthService {

  // Base URL for your Spring Boot backend's authentication endpoints
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  login(payload: LoginPayload): Observable<LoginResponse> {
    // Sending a POST request to the /api/auth/login endpoint
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, payload);
  }


  register(payload: LoginPayload): Observable<any> { 
    // Sending a POST request to the /api/auth/register endpoint
    return this.http.post<any>(`${this.baseUrl}/register`, payload);
  }

 
}
