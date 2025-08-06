// import { Component } from '@angular/core';
// import { Router, RouterLink } from '@angular/router';
// import { FormControl, FormGroup,Validators, ReactiveFormsModule } from '@angular/forms';
// import { IntegrationService } from '../../services/integration.service';
// import { LoginRequest } from '../../models/login-request';
// import { LocalStorageService } from '../../services/local-storage.service';
// import { inject } from '@angular/core';
// @Component({
//   selector: 'app-login',
//   standalone: true,
//   imports: [ReactiveFormsModule],
//   templateUrl: './login.component.html',
//   styleUrl: './login.component.scss',
// })
// export class LoginComponent {

  
//   constructor(private integration: IntegrationService,  private storage : LocalStorageService) {}

//   userForm : FormGroup =  new FormGroup({
//     username: new FormControl('', Validators.required),
//     password: new FormControl('', [Validators.required, Validators.minLength(4)])
//   });

//   router = inject(Router);
//   request: LoginRequest = new LoginRequest;

//   login() {

//     this.storage.remove('auth-key');
    
//     const formValue =  this.userForm.value;

//     if(formValue.username == '' || formValue.password == '') {
//       alert('Wrong Credentilas');
//       return;
//     }

//     this.request.username = formValue.username;
//     this.request.password = formValue.password;


//     this.integration.doLogin(this.request).subscribe({
//       next:(res: any) => {
//         console.log("Received Response:"+res.token);

//         this.storage.set('auth-key', res.token);

//         this.integration.home().subscribe({
//           next: (homeres:any) => {
//             console.log("Home res:"+homeres.response);

//             this.router.navigateByUrl('home');
//           }, error : (err) => {
//             console.log("Home error received :" + err);
//             this.storage.remove('auth-key');
//           }
//         });
//       }, error: (err) => {
//         console.log("Error Received Response:"+err);
//         this.storage.remove('auth-key');
//       }
//     });
  
// }}



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

  // New properties for handling the message box
  message: string = '';
  isError: boolean = false;

  login() {
    this.storage.remove('auth-key');
    const formValue = this.userForm.value;

    // Check if the form is invalid using the built-in validators
    if (this.userForm.invalid) {
      this.showMessage('Please fill all the required details.', true);
      return;
    }

    this.request.username = formValue.username;
    this.request.password = formValue.password;

    this.integration.doLogin(this.request).subscribe({
      next: (res: any) => {
        console.log("Received Response:" + res.token);
        this.storage.set('auth-key', res.token);

        this.integration.home().subscribe({
          next: (homeres: any) => {
            console.log("Home res:" + homeres.response);
            // Display success message before navigating
            this.showMessage('Login successful!', false);
            // Wait for a moment before navigating to allow the message to be seen
            setTimeout(() => {
              this.router.navigateByUrl('home');
            }, 1000);
          },
          error: (err) => {
            console.log("Home error received :" + err);
            this.storage.remove('auth-key');
            this.showMessage('Incorrect credentials.', true);
          }
        });
      },
      error: (err) => {
        console.log("Error Received Response:" + err);
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





// import { Component } from '@angular/core';
// import { Router, RouterLink } from '@angular/router';
// import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
// import { IntegrationService } from '../../services/integration.service';
// import { LoginRequest } from '../../models/login-request';
// import { LocalStorageService } from '../../services/local-storage.service';
// import { inject } from '@angular/core';
// import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar'; // Import MatSnackBar for notifications

// @Component({
//   selector: 'app-login',
//   standalone: true,
//   imports: [ReactiveFormsModule, MatSnackBarModule],
//   templateUrl: './login.component.html',
//   styleUrl: './login.component.scss',
// })
// export class LoginComponent {

//   constructor(
//     private integration: IntegrationService,
//     private storage: LocalStorageService,
//     private snackBar: MatSnackBar // Inject MatSnackBar
//   ) {}

//   userForm: FormGroup = new FormGroup({
//     username: new FormControl('', Validators.required),
//     password: new FormControl('', [Validators.required, Validators.minLength(4)])
//   });

//   router = inject(Router);
//   request: LoginRequest = new LoginRequest();

//   // Helper method to show snackbar messages
//   showMessage(message: string, isError: boolean = false): void {
//     this.snackBar.open(message, 'Close', {
//       duration: 5000,
//       panelClass: isError ? ['error-snackbar'] : ['success-snackbar']
//     });
//   }

//   login() {
//     this.storage.remove('auth-key');
//     const formValue = this.userForm.value;

//     // Check for empty fields
//     if (formValue.username === '' || formValue.password === '') {
//       this.showMessage('Please enter all fields to login', true);
//       return;
//     }

//     this.request.username = formValue.username;
//     this.request.password = formValue.password;

//     this.integration.doLogin(this.request).subscribe({
//       next: (res: any) => {
//         if (res.token) {
//           this.storage.set('auth-key', res.token);
//           this.showMessage('Login successful!');
          
//           this.integration.home().subscribe({
//             next: (homeres: any) => {
//               console.log("Home res:" + homeres.response);
//               this.router.navigateByUrl('home');
//             },
//             error: (err) => {
//               console.log("Home error received:", err);
//               this.storage.remove('auth-key');
//               this.showMessage('Authentication failed', true);
//             }
//           });
//         } else {
//           this.showMessage('Incorrect credentials', true);
//         }
//       },
//       error: (err) => {
//         console.log("Error Received Response:", err);
//         this.storage.remove('auth-key');
//         if (err.status === 401 || err.status === 403) {
//           this.showMessage('Incorrect credentials', true);
//         } else {
//           this.showMessage('Login failed. Please try again later.', true);
//         }
//       }
//     });
//   }
// }

