import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChallenge } from 'app/shared/model/challenge.model';
import { ChallengeService } from './challenge.service';
import { ChallengeDeleteDialogComponent } from './challenge-delete-dialog.component';

@Component({
  selector: 'jhi-challenge',
  templateUrl: './challenge.component.html'
})
export class ChallengeComponent implements OnInit, OnDestroy {
  challenges?: IChallenge[];
  eventSubscriber?: Subscription;

  constructor(protected challengeService: ChallengeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.challengeService.query().subscribe((res: HttpResponse<IChallenge[]>) => (this.challenges = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChallenges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChallenge): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChallenges(): void {
    this.eventSubscriber = this.eventManager.subscribe('challengeListModification', () => this.loadAll());
  }

  delete(challenge: IChallenge): void {
    const modalRef = this.modalService.open(ChallengeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.challenge = challenge;
  }
}
