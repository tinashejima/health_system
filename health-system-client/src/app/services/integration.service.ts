import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../models/login-request';
import { Observable } from 'rxjs';


const API_URL = 'http://localhost:8080/api/auth';


@Injectable({
  providedIn: 'root'
})
export class IntegrationService {


  constructor(private http: HttpClient) { }

  doLogin(request:LoginRequest):Observable<LoginRequest>{
    return this.http.post<LoginRequest>(API_URL + "/login", request);
  }
  dashboard(): Observable<any> {
    return this.http.get<any>(API_URL + "/dashboard");
  }

  // doRegister(request: SignupRequest):Observable<SignupResponse> {
  //   return this.http.post<SignupResponse>(API_URL + "/doRegister", request);
  // }
  
}
