import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChallenge, Challenge } from 'app/shared/model/challenge.model';
import { ChallengeService } from './challenge.service';
import { ChallengeComponent } from './challenge.component';
import { ChallengeDetailComponent } from './challenge-detail.component';
import { ChallengeUpdateComponent } from './challenge-update.component';

@Injectable({ providedIn: 'root' })
export class ChallengeResolve implements Resolve<IChallenge> {
  constructor(private service: ChallengeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChallenge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((challenge: HttpResponse<Challenge>) => {
          if (challenge.body) {
            return of(challenge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Challenge());
  }
}

export const challengeRoute: Routes = [
  {
    path: '',
    component: ChallengeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wirvsvirusApp.challenge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChallengeDetailComponent,
    resolve: {
      challenge: ChallengeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wirvsvirusApp.challenge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChallengeUpdateComponent,
    resolve: {
      challenge: ChallengeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wirvsvirusApp.challenge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChallengeUpdateComponent,
    resolve: {
      challenge: ChallengeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wirvsvirusApp.challenge.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
