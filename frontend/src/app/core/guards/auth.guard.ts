import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../features/auth/service/auth.service';

export const authGuard: CanActivateFn = (_route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isLogin()) {
    return router.createUrlTree(['/login'], {
      queryParams: { returnUrl: state.url },
    });
  }

  if (state.url.startsWith('/manage')) {
    if (!(authService.authUI() == 'ADMIN')) {
      return router.createUrlTree(['/home'], {
        queryParams: { returnUrl: state.url },
      });
    }
  }

  return true;
};
