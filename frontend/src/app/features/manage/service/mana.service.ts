import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiService } from '../../../core/services/api.service';
import { PageResponse, Student } from '../student/student.model';

@Injectable({
  providedIn: 'root',
})
export class ManageEntity {
  private readonly apiService = inject(apiService);

  getPageUser(page: number = 0, size: number = 10): Observable<PageResponse<Student>> {
    return this.apiService.get<PageResponse<Student>>('/students', {
      params: {
        page,
        size,
      },
      withCredentials: true,
    });
  }
}
