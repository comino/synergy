import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChallenge } from 'app/shared/model/challenge.model';

@Component({
  selector: 'jhi-challenge-detail',
  templateUrl: './challenge-detail.component.html'
})
export class ChallengeDetailComponent implements OnInit {
  challenge: IChallenge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ challenge }) => (this.challenge = challenge));
  }

  previousState(): void {
    window.history.back();
  }
}
