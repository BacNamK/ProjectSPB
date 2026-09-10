export interface Student {
  studentCode: string;
  name: string;
  full_name: string;
  gender: string;
  phone: string;
  email: string;
  role: string;
  stautus: string;
  classId: number | null;
  enrollmentYear: number;
  gpa: number | null;
}

export interface CreateStudentRequest {
  studentCode: string;
  passWord: string;
  name: string;
  email: string;
  fullName: string;
  phone: string;
  gender: 'MALE' | 'FEMALE' | 'OTHER';
}

export interface PageResponse<T> {
  data: T[];
  currentPage: number;
  pageSize: number;
  totalPages: number;
  totalItems: number;
}
