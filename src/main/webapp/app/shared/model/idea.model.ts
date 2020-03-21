import { IProject } from 'app/shared/model/project.model';
import { IChallenge } from 'app/shared/model/challenge.model';

export interface IIdea {
  id?: number;
  title?: string;
  problems?: string;
  description?: string;
  solution?: string;
  targetAudience?: string;
  stakeHolder?: string;
  slackChannel?: string;
  ministryProject?: boolean;
  projects?: IProject[];
  challenge?: IChallenge;
}

export class Idea implements IIdea {
  constructor(
    public id?: number,
    public title?: string,
    public problems?: string,
    public description?: string,
    public solution?: string,
    public targetAudience?: string,
    public stakeHolder?: string,
    public slackChannel?: string,
    public ministryProject?: boolean,
    public projects?: IProject[],
    public challenge?: IChallenge
  ) {
    this.ministryProject = this.ministryProject || false;
  }
}
