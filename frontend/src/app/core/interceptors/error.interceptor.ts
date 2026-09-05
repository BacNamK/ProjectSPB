import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core/primitives/di';
import { AuthService } from '../../features/auth/service/auth.service';
import { catchError, switchMap, throwError } from 'rxjs';

export const ErrorInterceptor: HttpInterceptorFn = (request, next) => {
  const authService = inject(AuthService);

  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      const isRefreshRequest = request.url.endsWith('/auth/refreshToken');

      if (error.status === 401 && !isRefreshRequest) {
        return authService.refreshToken().pipe(
          switchMap((response: { accessToken: string }) => {
            authService.setAccessToken(response.accessToken);
            const clonedRequest = request.clone({
              setHeaders: {
                Authorization: `Bearer ${response.accessToken}`,
              },
              withCredentials: true,
            });
            return next(clonedRequest);
          }),
        );
      }
      return throwError(() => error);
    }),
  );
};
