import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core/primitives/di';
import { AuthService } from '../../features/auth/service/auth.service';
import { catchError, switchMap, throwError } from 'rxjs';

export const ErrorInterceptor: HttpInterceptorFn = (request, next) => {
  const authService = inject(AuthService);

  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        return authService.refreshToken().pipe(
          switchMap((response: { accessToken: string }) => {
            authService.setAccessToken(response.accessToken);
            const clonedRequest = request.clone({
              setHeaders: {
                Authorization: `Bearer ${response.accessToken}`,
              },
            });
            return next(clonedRequest);
          }),
        );
      }
      return throwError(() => error);
    }),
  );
};
