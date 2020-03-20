import { ISkill } from 'app/shared/model/skill.model';
import { IProject } from 'app/shared/model/project.model';

export interface ITask {
  id?: number;
  name?: string;
  description?: string;
  skills?: ISkill[];
  projects?: IProject[];
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public skills?: ISkill[],
    public projects?: IProject[]
  ) {}
}
