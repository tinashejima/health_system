
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { IntegrationService } from '../../services/integration.service';
import { LoginRequest } from '../../models/login-request';
import { LocalStorageService } from '../../services/local-storage.service';
import { inject } from '@angular/core';
import { NgIf } from '@angular/common'; // Import NgIf for conditional rendering


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf], // Add NgIf to imports
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {

  constructor(
    private integration: IntegrationService,
    private storage: LocalStorageService
  ) {}

  userForm: FormGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, Validators.minLength(4)])
  });

  router = inject(Router);
  request: LoginRequest = new LoginRequest;

  //properties for handling the message box
  message: string = '';
  isError: boolean = false;

     login() {
 this.storage.remove('auth-key');
 const formValue = this.userForm.value;

 if (this.userForm.invalid) {
   this.showMessage('Please fill all the required details.', true);
   return;
 }

 this.request.username = formValue.username;
 this.request.password = formValue.password;

 this.integration.doLogin(this.request).subscribe({
   next: (res: any) => {
     console.log("Received Response:", res);
     const token = res.jwt
     console.log("Received Token:", token);

     if (token) {
       this.storage.set('auth-key', token);

       this.integration.home().subscribe({
         next: (homeres: any) => {
           console.log("Home res:" + homeres.response);
           this.showMessage('Login successful!', false);
           setTimeout(() => {
             this.router.navigateByUrl('/home');
           }, 1000);
         },
         error: (err) => {
           console.log("Home error received :", err);
           this.storage.remove('auth-key');
           this.showMessage('Incorrect credentials.', true);
         }
       });
     } else {
       this.showMessage('Incorrect credentials.', true);
     }
   },
   error: (err) => {
     console.log("Error Received Response:", err);
     this.storage.remove('auth-key');
     this.showMessage('Incorrect credentials.', true);
   }
 });
}
  // A method to display the message in the UI
  showMessage(message: string, isError: boolean) {
    this.message = message;
    this.isError = isError;

    // Clear the message after a few seconds
    setTimeout(() => {
      this.message = '';
    }, 4000);
  }
}


