import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { apiService } from '../../../../core/services/api.service';
import { LoginRequest, LoginResponse } from '../../models/auth.model';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
})
export class LoginComponent {
  constructor(private router: Router) {}

  private readonly apiService = inject(apiService);

  login(data: LoginRequest): void {
    this.apiService.post<LoginResponse>('auth/login', data).subscribe({
      next: (response) => {},
      error: (error) => {
        // Handle login error
      },
    });
  }
}
