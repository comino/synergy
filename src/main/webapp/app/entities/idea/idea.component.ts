import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from './idea.service';
import { IdeaDeleteDialogComponent } from './idea-delete-dialog.component';

@Component({
  selector: 'jhi-idea',
  templateUrl: './idea.component.html'
})
export class IdeaComponent implements OnInit, OnDestroy {
  ideas?: IIdea[];
  eventSubscriber?: Subscription;

  constructor(protected ideaService: IdeaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ideaService.query().subscribe((res: HttpResponse<IIdea[]>) => (this.ideas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIdeas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIdea): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIdeas(): void {
    this.eventSubscriber = this.eventManager.subscribe('ideaListModification', () => this.loadAll());
  }

  delete(idea: IIdea): void {
    const modalRef = this.modalService.open(IdeaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.idea = idea;
  }
}
