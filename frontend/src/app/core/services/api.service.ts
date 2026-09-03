import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpContext, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

type RequestOptions = {
  headers?: HttpHeaders | Record<string, string | string[]>;
  params?:
    HttpParams | Record<string, string | number | boolean | readonly (string | number | boolean)[]>;
  context?: HttpContext;
  withCredentials?: boolean;
};

@Injectable({ providedIn: 'root' })
export class apiService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api';

  get<T>(path: string, options?: RequestOptions): Observable<T> {
    return this.http.get<T>(this.url(path), options);
  }

  post<T>(path: string, body: unknown, options?: RequestOptions): Observable<T> {
    return this.http.post<T>(this.url(path), body, options);
  }

  put<T>(path: string, body: unknown, options?: RequestOptions): Observable<T> {
    return this.http.put<T>(this.url(path), body, options);
  }

  delete<T>(path: string, options?: RequestOptions): Observable<T> {
    return this.http.delete<T>(this.url(path), options);
  }

  private url(path: string): string {
    return `${this.apiUrl}/${path.replace(/^\/+/, '')}`;
  }
}
