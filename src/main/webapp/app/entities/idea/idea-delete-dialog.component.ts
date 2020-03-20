import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from './idea.service';

@Component({
  templateUrl: './idea-delete-dialog.component.html'
})
export class IdeaDeleteDialogComponent {
  idea?: IIdea;

  constructor(protected ideaService: IdeaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ideaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ideaListModification');
      this.activeModal.close();
    });
  }
}
