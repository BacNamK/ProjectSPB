import { inject, Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { apiService } from '../../../core/services/api.service';
import { CreateStudentRequest, PageResponse, Student } from '../students/student.model';
import { ApiResponse } from '../../../core/type.api';

@Injectable({
  providedIn: 'root',
})
export class ManageEntity {
  private readonly apiService = inject(apiService);

  getPageUser(page: number = 0, size: number = 10): Observable<PageResponse<Student>> {
    return this.apiService
      .get<ApiResponse<PageResponse<Student>> | PageResponse<Student>>('/students', {
        params: {
          page,
          size,
        },
        withCredentials: true,
      })
      .pipe(map((response) => ('success' in response ? response.data : response)));
  }

  getUserByField(field: string, value: string): Observable<Student[]> {
    return this.apiService
      .get<ApiResponse<Student[]> | Student[]>('/students/search', {
        params: {
          [field]: value,
        },
        withCredentials: true,
      })
      .pipe(map((response) => ('success' in response ? response.data : response)));
  }

  addStudent(
    data: CreateStudentRequest,
  ): Observable<{ message: string; user: string; studentCode: string }> {
    return this.apiService.post<{ message: string; user: string; studentCode: string }>(
      '/auth/add',
      data,
      { withCredentials: true },
    );
  }

  updateStudent(studentCode: string, fields: Partial<Student>): Observable<ApiResponse<string>> {
    return this.apiService.patch<ApiResponse<string>>(`/students/${studentCode}`, fields, {
      withCredentials: true,
    });
  }

  deleteStudent(studentCode: string): Observable<ApiResponse<string>> {
    return this.apiService.delete<ApiResponse<string>>(`/students/${studentCode}`, {
      withCredentials: true,
    });
  }
}
