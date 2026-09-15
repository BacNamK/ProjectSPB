import { inject, Injectable } from '@angular/core';
import { apiService } from '../../../core/services/api.service';
import { LoginRequest, LoginResponse } from '../models/auth.model';
import { Observable, tap } from 'rxjs';
import { AuthStore } from '../../stores/auth.stores';
import { User } from '../../../ui/users/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiService = inject(apiService);
  private readonly authStore = inject(AuthStore);

  login(data: LoginRequest): Observable<LoginResponse> {
    return this.apiService.post<LoginResponse>('/auth/login', data, { withCredentials: true }).pipe(
      tap((response) => {
        this.authStore.setAccessToken(response.accessToken);
        this.authStore.setUser(response.user);
      }),
    );
  }

  setAccessToken(accessToken: string): void {
    this.authStore.setAccessToken(accessToken);
  }

  isLogin(): boolean {
    return this.authStore.isAuthenticated();
  }

  refreshToken(): Observable<{ accessToken: string }> {
    // 1. Added the 'return' keyword
    return this.apiService
      .get<{ accessToken: string }>('/auth/refresh', { withCredentials: true })
      .pipe(
        // 2. Replaced .subscribe() with .pipe(tap(...))
        tap({
          next: (response) => {
            this.authStore.setAccessToken(response.accessToken);
          },
          error: (error) => {
            this.authStore.clear();
          },
        }),
      );
  }

  signOut() {
    this.apiService.get('/fetch/logout', { withCredentials: true }).subscribe({
      error: (error) => console.error('Đăng xuất thất bại', error),
    });
  }

  authUI(): string {
    const user: User | null = this.authStore.readUserP();
    return user?.role ?? 'GUEST';
  }
}
