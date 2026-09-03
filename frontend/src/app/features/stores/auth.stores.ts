import { signal, computed, Injectable } from '@angular/core';
import { User } from '../users/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthStore {
  private readonly _accessToken = signal<string | null>(null);

  readonly accessToken = this._accessToken.asReadonly();

  private _user = signal<User | null>(null);

  readonly isAuthenticated = computed(() => !!this._accessToken() && !!this._user());

  setAccessToken(token: string | null): void {
    this._accessToken.set(token);
  }

  setUser(user: User | null): void {
    this._user.set(user);
  }

  clear(): void {
    this._accessToken.set(null);
    this._user.set(null);
  }
}
