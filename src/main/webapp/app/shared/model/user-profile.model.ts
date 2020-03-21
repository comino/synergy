import { IUser } from 'app/core/user/user.model';
import { ITask } from 'app/shared/model/task.model';
import { ISkill } from 'app/shared/model/skill.model';

export interface IUserProfile {
  id?: number;
  github?: string;
  twitter?: string;
  user?: IUser;
  tasks?: ITask[];
  skills?: ISkill[];
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public github?: string,
    public twitter?: string,
    public user?: IUser,
    public tasks?: ITask[],
    public skills?: ISkill[]
  ) {}
}
