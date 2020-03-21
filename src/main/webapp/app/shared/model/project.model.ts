import { IUserProfile } from 'app/shared/model/user-profile.model';
import { ISkill } from 'app/shared/model/skill.model';
import { ITask } from 'app/shared/model/task.model';
import { IIdea } from 'app/shared/model/idea.model';

export interface IProject {
  id?: number;
  name?: string;
  repository?: string;
  slack?: string;
  email?: string;
  description?: string;
  imageContentType?: string;
  image?: any;
  userProfiles?: IUserProfile[];
  skills?: ISkill[];
  tasks?: ITask[];
  idea?: IIdea;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public name?: string,
    public repository?: string,
    public slack?: string,
    public email?: string,
    public description?: string,
    public imageContentType?: string,
    public image?: any,
    public userProfiles?: IUserProfile[],
    public skills?: ISkill[],
    public tasks?: ITask[],
    public idea?: IIdea
  ) {}
}
