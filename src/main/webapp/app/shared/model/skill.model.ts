import { IUserProfile } from 'app/shared/model/user-profile.model';
import { IProject } from 'app/shared/model/project.model';
import { ITask } from 'app/shared/model/task.model';

export interface ISkill {
  id?: number;
  name?: string;
  userProfiles?: IUserProfile[];
  projects?: IProject[];
  tasks?: ITask[];
}

export class Skill implements ISkill {
  constructor(
    public id?: number,
    public name?: string,
    public userProfiles?: IUserProfile[],
    public projects?: IProject[],
    public tasks?: ITask[]
  ) {}
}
