import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIdea, Idea } from 'app/shared/model/idea.model';
import { IdeaService } from './idea.service';
import { IChallenge } from 'app/shared/model/challenge.model';
import { ChallengeService } from 'app/entities/challenge/challenge.service';

@Component({
  selector: 'jhi-idea-update',
  templateUrl: './idea-update.component.html'
})
export class IdeaUpdateComponent implements OnInit {
  isSaving = false;
  challenges: IChallenge[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    problems: [null, [Validators.required]],
    description: [null, [Validators.required]],
    solution: [],
    targetAudience: [],
    stakeHolder: [],
    slackChannel: [],
    ministryProject: [],
    challenge: []
  });

  constructor(
    protected ideaService: IdeaService,
    protected challengeService: ChallengeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idea }) => {
      this.updateForm(idea);

      this.challengeService.query().subscribe((res: HttpResponse<IChallenge[]>) => (this.challenges = res.body || []));
    });
  }

  updateForm(idea: IIdea): void {
    this.editForm.patchValue({
      id: idea.id,
      title: idea.title,
      problems: idea.problems,
      description: idea.description,
      solution: idea.solution,
      targetAudience: idea.targetAudience,
      stakeHolder: idea.stakeHolder,
      slackChannel: idea.slackChannel,
      ministryProject: idea.ministryProject,
      challenge: idea.challenge
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const idea = this.createFromForm();
    if (idea.id !== undefined) {
      this.subscribeToSaveResponse(this.ideaService.update(idea));
    } else {
      this.subscribeToSaveResponse(this.ideaService.create(idea));
    }
  }

  private createFromForm(): IIdea {
    return {
      ...new Idea(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      problems: this.editForm.get(['problems'])!.value,
      description: this.editForm.get(['description'])!.value,
      solution: this.editForm.get(['solution'])!.value,
      targetAudience: this.editForm.get(['targetAudience'])!.value,
      stakeHolder: this.editForm.get(['stakeHolder'])!.value,
      slackChannel: this.editForm.get(['slackChannel'])!.value,
      ministryProject: this.editForm.get(['ministryProject'])!.value,
      challenge: this.editForm.get(['challenge'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdea>>): void {
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

  trackById(index: number, item: IChallenge): any {
    return item.id;
  }
}
