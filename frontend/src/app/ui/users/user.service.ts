import { inject, Injectable } from '@angular/core';
import { apiService } from '../../core/services/api.service';
import { map, Observable } from 'rxjs';
import { ApiResponse, User } from './user.model';
import { AuthStore } from '../../features/stores/auth.stores';

@Injectable({
  providedIn: 'root',
})
export class userService {
  private readonly apiService = inject(apiService);
  private readonly authStore = inject(AuthStore);

  fetchMe(): Observable<User> {
    return this.apiService
      .get<ApiResponse<User>>('/fetch/me')
      .pipe(map((response) => response.data));
  }
}
