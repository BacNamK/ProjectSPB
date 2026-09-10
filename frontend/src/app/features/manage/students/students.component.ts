import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Observable, map } from 'rxjs';
import { ManageEntity } from '../service/mana.service';
import { CreateStudentRequest, PageResponse, Student } from './student.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-manage-students',
  standalone: true,
  imports: [AsyncPipe, FormsModule, RouterLink],
  templateUrl: './students.componet.html',
})
export class students {
  private readonly manageEntity = inject(ManageEntity);
  page = 1;
  size = this.getPageSize();
  searchValue = '';
  students$ = this.getStudent();

  fields = [
    { id: 1, name: 'Mã sinh viên', field: 'studentCode' },
    { id: 2, name: 'Họ tên', field: 'full_name' },
    { id: 3, name: 'Điện thoại', field: 'phone' },
    { id: 4, name: 'Lớp', field: 'classId' },
  ];
  selectedField = this.fields[0];
  isFieldMenuOpen = false;
  isAddModalOpen = false;
  isSubmitting = false;
  status = false;
  addError = '';
  addForm: CreateStudentRequest = this.emptyAddForm();

  openAddModal(): void {
    this.addForm = this.emptyAddForm();
    this.addError = '';
    this.status = false;
    this.isAddModalOpen = true;
  }

  closeAddModal(): void {
    if (!this.isSubmitting) {
      this.isAddModalOpen = false;
    }
  }

  addStudent(form: NgForm): void {
    if (
      form == null ||
      form.invalid ||
      Object.values(this.addForm).some((value) => String(value).trim() === '')
    ) {
      this.addError = 'Vui lòng nhập đầy đủ thông tin.';
      return;
    }

    this.isSubmitting = true;
    this.addError = '';

    this.manageEntity.addStudent(this.addForm).subscribe({
      next: () => {
        this.isSubmitting = false;
        form.resetForm(this.emptyAddForm());
        this.status = true;

        setTimeout(() => {
          this.status = false;
        }, 2000);

        this.students$ = this.getStudent();
      },
      error: (error) => {
        this.isSubmitting = false;
        this.status = false;
        this.addError = error?.error?.message ?? 'Không thể thêm sinh viên.';
      },
    });
  }

  private emptyAddForm(): CreateStudentRequest {
    return {
      studentCode: '',
      passWord: '',
      name: '',
      email: '',
      fullName: '',
      phone: '',
      gender: 'MALE',
    };
  }

  searchStudents(): void {
    const value = this.searchValue.trim();

    if (!value) {
      this.students$ = this.getStudent();
      return;
    }

    this.students$ = this.manageEntity.getUserByField(this.selectedField.field, value).pipe(
      map((students: Student[]): PageResponse<Student> => ({
        data: students,
        currentPage: 1,
        pageSize: students.length,
        totalPages: 1,
        totalItems: students.length,
      })),
    );
  }

  selectField(field: (typeof this.fields)[number]): void {
    this.selectedField = field;
    this.isFieldMenuOpen = false;
  }

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
