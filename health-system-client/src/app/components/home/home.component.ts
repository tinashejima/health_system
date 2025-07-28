import { Component } from '@angular/core';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [LoginComponent],
  template: `
    <div class="home-container">
      <app-login/>
    </div>
  `,
  styles: [`
    .home-container {
      width: 100%;
      height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: #f5f5f5;
    }
  `]
})
export class HomeComponent { }
