import type { Role } from './Role';

export interface UserItem {
  id: string;
  username: string;
  email: string;
  password: string;
  picture: string;
  roles: Role[];
}
