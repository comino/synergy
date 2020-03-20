import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from './skill.service';
import { SkillDeleteDialogComponent } from './skill-delete-dialog.component';

@Component({
  selector: 'jhi-skill',
  templateUrl: './skill.component.html'
})
export class SkillComponent implements OnInit, OnDestroy {
  skills?: ISkill[];
  eventSubscriber?: Subscription;

  constructor(protected skillService: SkillService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.skillService.query().subscribe((res: HttpResponse<ISkill[]>) => (this.skills = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSkills();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISkill): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSkills(): void {
    this.eventSubscriber = this.eventManager.subscribe('skillListModification', () => this.loadAll());
  }

  delete(skill: ISkill): void {
    const modalRef = this.modalService.open(SkillDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.skill = skill;
  }
}
