import { User } from '../../users/models/user.model';

export interface LoginRequest {
  userCode: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  user: User;
}
