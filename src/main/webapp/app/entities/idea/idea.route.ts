import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIdea, Idea } from 'app/shared/model/idea.model';
import { IdeaService } from './idea.service';
import { IdeaComponent } from './idea.component';
import { IdeaDetailComponent } from './idea-detail.component';
import { IdeaUpdateComponent } from './idea-update.component';

@Injectable({ providedIn: 'root' })
export class IdeaResolve implements Resolve<IIdea> {
  constructor(private service: IdeaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIdea> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((idea: HttpResponse<Idea>) => {
          if (idea.body) {
            return of(idea.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Idea());
  }
}

export const ideaRoute: Routes = [
  {
    path: '',
    component: IdeaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wirvsvirusApp.idea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IdeaDetailComponent,
    resolve: {
      idea: IdeaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wirvsvirusApp.idea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IdeaUpdateComponent,
    resolve: {
      idea: IdeaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wirvsvirusApp.idea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IdeaUpdateComponent,
    resolve: {
      idea: IdeaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wirvsvirusApp.idea.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
