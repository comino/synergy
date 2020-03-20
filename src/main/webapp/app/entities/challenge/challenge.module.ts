import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WirvsvirusSharedModule } from 'app/shared/shared.module';
import { ChallengeComponent } from './challenge.component';
import { ChallengeDetailComponent } from './challenge-detail.component';
import { ChallengeUpdateComponent } from './challenge-update.component';
import { ChallengeDeleteDialogComponent } from './challenge-delete-dialog.component';
import { challengeRoute } from './challenge.route';

@NgModule({
  imports: [WirvsvirusSharedModule, RouterModule.forChild(challengeRoute)],
  declarations: [ChallengeComponent, ChallengeDetailComponent, ChallengeUpdateComponent, ChallengeDeleteDialogComponent],
  entryComponents: [ChallengeDeleteDialogComponent]
})
export class WirvsvirusChallengeModule {}
