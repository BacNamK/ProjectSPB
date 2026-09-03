import { signal, computed, Injectable } from '@angular/core';
import { User } from '../users/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthStore {
  private readonly accessTokenStorageKey = 'auth_access_token';
  private readonly userStorageKey = 'auth_user';

  private readonly _accessToken = signal<string | null>(this.readAccessToken());

  readonly accessToken = this._accessToken.asReadonly();

  private _user = signal<User | null>(this.readUser());

  readonly isAuthenticated = computed(() => !!this._accessToken() && !!this._user());

  setAccessToken(token: string | null): void {
    this._accessToken.set(token);

    if (token) {
      this.writeStorage(this.accessTokenStorageKey, token);
    } else {
      this.removeStorage(this.accessTokenStorageKey);
    }
  }

  setUser(user: User | null): void {
    this._user.set(user);

    if (user) {
      this.writeStorage(this.userStorageKey, JSON.stringify(user));
    } else {
      this.removeStorage(this.userStorageKey);
    }
  }

  clear(): void {
    this.setAccessToken(null);
    this.setUser(null);
  }

  private readAccessToken(): string | null {
    return this.storage?.getItem(this.accessTokenStorageKey) ?? null;
  }

  private readUser(): User | null {
    const storedUser = this.storage?.getItem(this.userStorageKey);

    if (!storedUser) {
      return null;
    }

    try {
      return JSON.parse(storedUser) as User;
    } catch {
      this.removeStorage(this.userStorageKey);
      return null;
    }
  }

  private writeStorage(key: string, value: string): void {
    this.storage?.setItem(key, value);
  }

  private removeStorage(key: string): void {
    this.storage?.removeItem(key);
  }

  private get storage(): Storage | null {
    return typeof localStorage === 'undefined' ? null : localStorage;
  }
}
