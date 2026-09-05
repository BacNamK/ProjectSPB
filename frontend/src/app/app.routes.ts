import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { MainLayout } from './layout/main.layout';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/pages/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: '',
    component: MainLayout,
    canActivate: [authGuard],
    children: [
      {
        path: 'home',
        loadComponent: () => import('./ui/home/home.component').then((m) => m.HomePage),
      },
      {
        path: 'manage/student',
        loadComponent: () => import('./features/manage/student.componet').then((m) => m.student),
      },
    ],
  },
];
