import { inject, Injectable } from '@angular/core';
import { apiService } from '../../../core/services/api.service';
import { LoginRequest, LoginResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiService = inject(apiService);
  store: { accessToken: string; setAccessToken: (token: string) => void } = {
    accessToken: '',
    setAccessToken: (token: string) => {
      this.store.accessToken = token;
    },
  };

  login(data: LoginRequest): void {
    this.apiService.post<LoginResponse>('/auth/login', data, { withCredentials: true }).subscribe({
      next: (response) => {
        this.store.setAccessToken(response.accessToken);
      },
      error: (error) => {
        // Handle login error
      },
    });
  }

  refreshToken(): void {
    this.apiService
      .post<LoginResponse>('/auth/refresh-token', {}, { withCredentials: true })
      .subscribe({
        next: (response) => {
          this.store.setAccessToken(response.accessToken);
        },
        error: (error) => {
          // Handle refresh token error
        },
      });
  }
}
