import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WirvsvirusTestModule } from '../../../test.module';
import { ChallengeComponent } from 'app/entities/challenge/challenge.component';
import { ChallengeService } from 'app/entities/challenge/challenge.service';
import { Challenge } from 'app/shared/model/challenge.model';

describe('Component Tests', () => {
  describe('Challenge Management Component', () => {
    let comp: ChallengeComponent;
    let fixture: ComponentFixture<ChallengeComponent>;
    let service: ChallengeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WirvsvirusTestModule],
        declarations: [ChallengeComponent]
      })
        .overrideTemplate(ChallengeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChallengeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChallengeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Challenge(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.challenges && comp.challenges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
