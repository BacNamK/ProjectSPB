import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthStore } from '../../features/stores/auth.stores';

export const AuthInterceptor: HttpInterceptorFn = (request, next) => {
  const authStore = inject(AuthStore);

  const token = authStore.accessToken();

  if (!token) {
    return next(request);
  }

  const authRequest = request.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },

    withCredentials: true,
  });

  return next(authRequest);
};
