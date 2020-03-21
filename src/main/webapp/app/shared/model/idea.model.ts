import { IProject } from 'app/shared/model/project.model';
import { IChallenge } from 'app/shared/model/challenge.model';

export interface IIdea {
  id?: number;
  name?: string;
  description?: string;
  projects?: IProject[];
  challenge?: IChallenge;
}

export class Idea implements IIdea {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public projects?: IProject[],
    public challenge?: IChallenge
  ) {}
}
