import 'zone.js';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http'; // For HttpClient
import { provideRouter } from '@angular/router'; // For Router
import { routes } from './app/app.routes'; // Your routes file
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(), // Provide HttpClient
    provideRouter(routes) // Provide Router
  ]
}).catch(err => console.error(err));