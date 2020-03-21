import { IIdea } from 'app/shared/model/idea.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IChallenge {
  id?: number;
  name?: string;
  ideas?: IIdea[];
  categories?: ICategory[];
}

export class Challenge implements IChallenge {
  constructor(public id?: number, public name?: string, public ideas?: IIdea[], public categories?: ICategory[]) {}
}
