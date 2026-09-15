import { User } from '../../../ui/users/user.model';

export interface LoginRequest {
  code: string;
  passWord: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  user: User;
}
