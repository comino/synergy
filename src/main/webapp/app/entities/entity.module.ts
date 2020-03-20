import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'skill',
        loadChildren: () => import('./skill/skill.module').then(m => m.WirvsvirusSkillModule)
      },
      {
        path: 'user-profile',
        loadChildren: () => import('./user-profile/user-profile.module').then(m => m.WirvsvirusUserProfileModule)
      },
      {
        path: 'challenge',
        loadChildren: () => import('./challenge/challenge.module').then(m => m.WirvsvirusChallengeModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.WirvsvirusCategoryModule)
      },
      {
        path: 'idea',
        loadChildren: () => import('./idea/idea.module').then(m => m.WirvsvirusIdeaModule)
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.WirvsvirusProjectModule)
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.WirvsvirusTaskModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class WirvsvirusEntityModule {}
