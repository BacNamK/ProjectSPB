import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { ManageEntity } from './service/mana.service';
import { PageResponse, Student } from './student/student.model';

@Component({
  selector: 'app-manage-student',
  standalone: true,
  imports: [AsyncPipe, FormsModule],
  templateUrl: './student.componet.html',
})
export class student {
  private readonly manageEntity = inject(ManageEntity);
  page = 1;
  size = this.getPageSize();
  students$ = this.getStudent();

  getStudent(): Observable<PageResponse<Student>> {
    return this.manageEntity.getPageUser(this.page, this.size);
  }

  loadStudents(): void {
    this.page = Math.max(1, Math.trunc(Number(this.page) || 1));
    this.size = Math.min(50, Math.max(1, Math.trunc(Number(this.size) || 10)));
    this.setPageSize();
    this.students$ = this.getStudent();
  }

  goToPage(page: number, totalPages: number): void {
    this.page = Math.min(totalPages, Math.max(1, page));
    this.loadStudents();
  }

  getPageSize(): number {
    const page = localStorage.getItem('pageSize');
    if (!page) return 10;
    return Math.min(50, Math.max(1, Math.trunc(Number(page) || 10)));
  }

  setPageSize(): void {
    localStorage.setItem('pageSize', String(this.size));
  }
}
