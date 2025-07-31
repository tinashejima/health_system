import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'home', component: HomeComponent }



        // , canActivate: [AuthGuard]},
    // { path: 'register', component: RegisterComponent },
    // {
    //     path: '', component: LayoutComponent,
    //     children: [
    //         { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }
    //     ]
    // }
];