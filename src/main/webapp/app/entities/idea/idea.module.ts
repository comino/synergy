import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WirvsvirusSharedModule } from 'app/shared/shared.module';
import { IdeaComponent } from './idea.component';
import { IdeaDetailComponent } from './idea-detail.component';
import { IdeaUpdateComponent } from './idea-update.component';
import { IdeaDeleteDialogComponent } from './idea-delete-dialog.component';
import { ideaRoute } from './idea.route';

@NgModule({
  imports: [WirvsvirusSharedModule, RouterModule.forChild(ideaRoute)],
  declarations: [IdeaComponent, IdeaDetailComponent, IdeaUpdateComponent, IdeaDeleteDialogComponent],
  entryComponents: [IdeaDeleteDialogComponent]
})
export class WirvsvirusIdeaModule {}
