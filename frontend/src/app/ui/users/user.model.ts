export interface User {
  id?: number;
  classId?: number;
  name?: string;
  email?: string;
  enrollmentYear?: number;
  full_name?: string;
  gender?: string;
  gpa?: number;
  phone?: string;
  role?: 'STUDENT' | 'LETURER' | 'MODERATOR' | 'ADMIN' | string;
  stautus?: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}
