import { ICategory } from 'app/shared/model/category.model';
import { IIdea } from 'app/shared/model/idea.model';

export interface IChallenge {
  id?: number;
  name?: string;
  problems?: string;
  description?: string;
  solution?: string;
  targetAudience?: string;
  stakeHolder?: string;
  slackChannel?: string;
  ministryProject?: boolean;
  categories?: ICategory[];
  ideas?: IIdea[];
}

export class Challenge implements IChallenge {
  constructor(
    public id?: number,
    public name?: string,
    public problems?: string,
    public description?: string,
    public solution?: string,
    public targetAudience?: string,
    public stakeHolder?: string,
    public slackChannel?: string,
    public ministryProject?: boolean,
    public categories?: ICategory[],
    public ideas?: IIdea[]
  ) {
    this.ministryProject = this.ministryProject || false;
  }
}
