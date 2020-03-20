import { ISkill } from 'app/shared/model/skill.model';
import { IProject } from 'app/shared/model/project.model';

export interface IUserProfile {
  id?: number;
  github?: string;
  twitter?: string;
  skills?: ISkill[];
  projects?: IProject[];
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public github?: string,
    public twitter?: string,
    public skills?: ISkill[],
    public projects?: IProject[]
  ) {}
}
