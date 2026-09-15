import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../features/auth/service/auth.service';
import { AuthStore } from '../../features/stores/auth.stores';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.html',
})
export class navbar {
  private readonly authService = inject(AuthService);
  private readonly authStore = inject(AuthStore);
  private readonly router = inject(Router);

  isAdmin(): boolean {
    return this.authService.authUI() == 'ADMIN' ? !true : !false;
  }

  signOut() {
    try {
      this.authService.signOut();
      this.authStore.clear();
      this.router.navigate(['/login']);
    } catch (e) {
      console.log(e);
    }
  }
}
