import { ISkill } from 'app/shared/model/skill.model';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { IProject } from 'app/shared/model/project.model';

export interface ITask {
  id?: number;
  name?: string;
  description?: string;
  skills?: ISkill[];
  userProfile?: IUserProfile;
  projects?: IProject[];
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public skills?: ISkill[],
    public userProfile?: IUserProfile,
    public projects?: IProject[]
  ) {}
}
