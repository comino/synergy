import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChallenge, Challenge } from 'app/shared/model/challenge.model';
import { ChallengeService } from './challenge.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';

type SelectableEntity = ICategory | IIdea;

@Component({
  selector: 'jhi-challenge-update',
  templateUrl: './challenge-update.component.html'
})
export class ChallengeUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];
  ideas: IIdea[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    problems: [],
    description: [],
    solution: [],
    targetAudience: [],
    stakeHolder: [],
    slackChannel: [],
    ministryProject: [],
    categories: [],
    ideas: []
  });

  constructor(
    protected challengeService: ChallengeService,
    protected categoryService: CategoryService,
    protected ideaService: IdeaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ challenge }) => {
      this.updateForm(challenge);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));

      this.ideaService.query().subscribe((res: HttpResponse<IIdea[]>) => (this.ideas = res.body || []));
    });
  }

  updateForm(challenge: IChallenge): void {
    this.editForm.patchValue({
      id: challenge.id,
      name: challenge.name,
      problems: challenge.problems,
      description: challenge.description,
      solution: challenge.solution,
      targetAudience: challenge.targetAudience,
      stakeHolder: challenge.stakeHolder,
      slackChannel: challenge.slackChannel,
      ministryProject: challenge.ministryProject,
      categories: challenge.categories,
      ideas: challenge.ideas
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const challenge = this.createFromForm();
    if (challenge.id !== undefined) {
      this.subscribeToSaveResponse(this.challengeService.update(challenge));
    } else {
      this.subscribeToSaveResponse(this.challengeService.create(challenge));
    }
  }

  private createFromForm(): IChallenge {
    return {
      ...new Challenge(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      problems: this.editForm.get(['problems'])!.value,
      description: this.editForm.get(['description'])!.value,
      solution: this.editForm.get(['solution'])!.value,
      targetAudience: this.editForm.get(['targetAudience'])!.value,
      stakeHolder: this.editForm.get(['stakeHolder'])!.value,
      slackChannel: this.editForm.get(['slackChannel'])!.value,
      ministryProject: this.editForm.get(['ministryProject'])!.value,
      categories: this.editForm.get(['categories'])!.value,
      ideas: this.editForm.get(['ideas'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChallenge>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
