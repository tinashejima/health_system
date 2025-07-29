import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup,Validators, ReactiveFormsModule } from '@angular/forms';
import { IntegrationService } from '../../services/integration.service';
import { LoginRequest } from '../../models/login-request';
import { LocalStorageService } from '../../services/local-storage.service';
import { inject } from '@angular/core';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {

  
  constructor(private integration: IntegrationService,  private storage : LocalStorageService) {}

  userForm : FormGroup =  new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, Validators.minLength(4)])
  });

  router = inject(Router);
  request: LoginRequest = new LoginRequest;

  login() {

    this.storage.remove('auth-key');
    
    const formValue =  this.userForm.value;

    if(formValue.username == '' || formValue.password == '') {
      alert('Wrong Credentilas');
      return;
    }

    this.request.username = formValue.username;
    this.request.password = formValue.password;


    this.integration.doLogin(this.request).subscribe({
      next:(res: any) => {
        console.log("Received Response:"+res.token);

        this.storage.set('auth-key', res.token);

        this.integration.dashboard().subscribe({
          next: (dashboardres:any) => {
            console.log("Dashboard res:"+dashboardres.response);

            this.router.navigateByUrl('dashboard');
          }, error : (err) => {
            console.log("Dashboard error received :" + err); 
            this.storage.remove('auth-key');
          }
        });
      }, error: (err) => {
        console.log("Error Received Response:"+err);
        this.storage.remove('auth-key');
      }
    });
  
}}




