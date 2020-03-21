import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProject, Project } from 'app/shared/model/project.model';
import { ProjectService } from './project.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';

type SelectableEntity = IUser | ISkill | ITask | IIdea;

type SelectableManyToManyEntity = IUser | ISkill | ITask;

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html'
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  skills: ISkill[] = [];
  tasks: ITask[] = [];
  ideas: IIdea[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    repository: [],
    slack: [],
    email: [],
    description: [],
    image: [],
    imageContentType: [],
    users: [],
    skills: [],
    tasks: [],
    idea: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected projectService: ProjectService,
    protected userService: UserService,
    protected skillService: SkillService,
    protected taskService: TaskService,
    protected ideaService: IdeaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      this.updateForm(project);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.skillService.query().subscribe((res: HttpResponse<ISkill[]>) => (this.skills = res.body || []));

      this.taskService.query().subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body || []));

      this.ideaService.query().subscribe((res: HttpResponse<IIdea[]>) => (this.ideas = res.body || []));
    });
  }

  updateForm(project: IProject): void {
    this.editForm.patchValue({
      id: project.id,
      name: project.name,
      repository: project.repository,
      slack: project.slack,
      email: project.email,
      description: project.description,
      image: project.image,
      imageContentType: project.imageContentType,
      users: project.users,
      skills: project.skills,
      tasks: project.tasks,
      idea: project.idea
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('wirvsvirusApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const project = this.createFromForm();
    if (project.id !== undefined) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  private createFromForm(): IProject {
    return {
      ...new Project(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      repository: this.editForm.get(['repository'])!.value,
      slack: this.editForm.get(['slack'])!.value,
      email: this.editForm.get(['email'])!.value,
      description: this.editForm.get(['description'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      users: this.editForm.get(['users'])!.value,
      skills: this.editForm.get(['skills'])!.value,
      tasks: this.editForm.get(['tasks'])!.value,
      idea: this.editForm.get(['idea'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
