import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, map, switchMap } from 'rxjs';
import { ManageEntity } from '../../service/mana.service';
import { Student } from '../student.model';

@Component({
  selector: 'app-manage-student-detail',
  standalone: true,
  imports: [AsyncPipe, FormsModule],
  templateUrl: './studentDetail.component.html',
})
export class StudentDetail {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly manageEntity = inject(ManageEntity);

  student$: Observable<Student | undefined> = this.loadStudent();
  originalStudent: Partial<Student> = {};
  isEditing = false;
  isSaving = false;
  isDeleting = false;
  isDelete = false;
  errorMessage = '';

  private loadStudent(): Observable<Student | undefined> {
    return this.route.paramMap.pipe(
      switchMap((params) =>
        this.manageEntity.getUserByField('studentCode', params.get('studentCode') ?? ''),
      ),
      map((students) => students[0]),
    );
  }

  startEditing(student: Student): void {
    this.originalStudent = { ...student };
    this.errorMessage = '';
    this.isEditing = true;
  }

  cancelEditing(student: Student): void {
    Object.assign(student, this.originalStudent);
    this.errorMessage = '';
    this.isEditing = false;
  }

  save(student: Student): void {
    const changedFields = Object.fromEntries(
      Object.entries(student).filter(
        ([field, value]) =>
          field !== 'studentCode' && value !== this.originalStudent[field as keyof Student],
      ),
    ) as Partial<Student>;

    if (Object.keys(changedFields).length === 0) {
      this.isEditing = false;
      return;
    }

    this.isSaving = true;
    this.errorMessage = '';
    this.manageEntity.updateStudent(student.studentCode, changedFields).subscribe({
      next: () => {
        this.isSaving = false;
        this.isEditing = false;
        this.student$ = this.loadStudent();
      },
      error: (error) => {
        this.isSaving = false;
        this.errorMessage = error?.error?.message ?? 'Không thể cập nhật thông tin sinh viên.';
      },
    });
  }

  verifyAction(): void {
    this.isDelete = true;
  }

  delete(studentCode: string): void {
    this.isDeleting = true;
    this.errorMessage = '';

    this.manageEntity.deleteStudent(studentCode).subscribe({
      next: () => {
        this.isDeleting = false;
        this.isDelete = false;
        this.router.navigate(['/manage/students']);
      },
      error: (error) => {
        this.isDeleting = false;
        this.errorMessage = error?.error?.message ?? 'Không thể xóa sinh viên.';
      },
    });
  }
}
