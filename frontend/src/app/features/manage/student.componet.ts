import { Component, OnInit, inject } from '@angular/core';
import { ManageEntity } from './service/mana.service';
import { Student } from './student/student.model';

@Component({
  selector: 'app-manage-student',
  standalone: true,
  templateUrl: './student.componet.html',
})
export class student implements OnInit {
  private readonly manageEntity = inject(ManageEntity);

  students: Student[] = [];
  currentPage = 1;
  pageSize = 10;
  totalPages = 0;
  loading = false;
  error = '';

  ngOnInit(): void {
    this.getStudents();
  }

  getStudents(page = this.currentPage): void {
    this.loading = true;
    this.error = '';

    this.manageEntity.getPageUser(page, this.pageSize).subscribe({
      next: (response) => {
        const pageResponse = Array.isArray(response)
          ? {
              data: response,
              currentPage: page,
              pageSize: this.pageSize,
              totalPages: 1,
            }
          : response;

        this.students = pageResponse.data ?? [];
        this.currentPage = pageResponse.currentPage ?? page;
        this.pageSize = pageResponse.pageSize ?? this.pageSize;
        this.totalPages = pageResponse.totalPages || 1;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.error = 'Không thể tải danh sách sinh viên.';
      },
    });
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.getStudents(this.currentPage - 1);
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.getStudents(this.currentPage + 1);
    }
  }
}
