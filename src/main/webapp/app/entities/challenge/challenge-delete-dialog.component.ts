import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChallenge } from 'app/shared/model/challenge.model';
import { ChallengeService } from './challenge.service';

@Component({
  templateUrl: './challenge-delete-dialog.component.html'
})
export class ChallengeDeleteDialogComponent {
  challenge?: IChallenge;

  constructor(protected challengeService: ChallengeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.challengeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('challengeListModification');
      this.activeModal.close();
    });
  }
}
