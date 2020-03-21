import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';

type SelectableEntity = IUser | ISkill;

@Component({
  selector: 'jhi-user-profile-update',
  templateUrl: './user-profile-update.component.html'
})
export class UserProfileUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  skills: ISkill[] = [];

  editForm = this.fb.group({
    id: [],
    github: [],
    twitter: [],
    user: [],
    skills: []
  });

  constructor(
    protected userProfileService: UserProfileService,
    protected userService: UserService,
    protected skillService: SkillService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      this.updateForm(userProfile);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.skillService.query().subscribe((res: HttpResponse<ISkill[]>) => (this.skills = res.body || []));
    });
  }

  updateForm(userProfile: IUserProfile): void {
    this.editForm.patchValue({
      id: userProfile.id,
      github: userProfile.github,
      twitter: userProfile.twitter,
      user: userProfile.user,
      skills: userProfile.skills
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userProfile = this.createFromForm();
    if (userProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.userProfileService.update(userProfile));
    } else {
      this.subscribeToSaveResponse(this.userProfileService.create(userProfile));
    }
  }

  private createFromForm(): IUserProfile {
    return {
      ...new UserProfile(),
      id: this.editForm.get(['id'])!.value,
      github: this.editForm.get(['github'])!.value,
      twitter: this.editForm.get(['twitter'])!.value,
      user: this.editForm.get(['user'])!.value,
      skills: this.editForm.get(['skills'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfile>>): void {
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

  getSelected(selectedVals: ISkill[], option: ISkill): ISkill {
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
