export interface User {
  user_code: string;
  name: string;
  role: 'admin' | 'user' | 'moderator';
}
