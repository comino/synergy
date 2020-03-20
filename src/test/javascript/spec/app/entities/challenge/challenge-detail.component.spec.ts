import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WirvsvirusTestModule } from '../../../test.module';
import { ChallengeDetailComponent } from 'app/entities/challenge/challenge-detail.component';
import { Challenge } from 'app/shared/model/challenge.model';

describe('Component Tests', () => {
  describe('Challenge Management Detail Component', () => {
    let comp: ChallengeDetailComponent;
    let fixture: ComponentFixture<ChallengeDetailComponent>;
    const route = ({ data: of({ challenge: new Challenge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WirvsvirusTestModule],
        declarations: [ChallengeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChallengeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChallengeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load challenge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.challenge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
