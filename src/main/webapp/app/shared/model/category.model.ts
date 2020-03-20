import { IChallenge } from 'app/shared/model/challenge.model';

export interface ICategory {
  id?: number;
  name?: string;
  challenges?: IChallenge[];
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public challenges?: IChallenge[]) {}
}
