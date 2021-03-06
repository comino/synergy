import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WirvsvirusTestModule } from '../../../test.module';
import { ChallengeUpdateComponent } from 'app/entities/challenge/challenge-update.component';
import { ChallengeService } from 'app/entities/challenge/challenge.service';
import { Challenge } from 'app/shared/model/challenge.model';

describe('Component Tests', () => {
  describe('Challenge Management Update Component', () => {
    let comp: ChallengeUpdateComponent;
    let fixture: ComponentFixture<ChallengeUpdateComponent>;
    let service: ChallengeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WirvsvirusTestModule],
        declarations: [ChallengeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ChallengeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChallengeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChallengeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Challenge(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Challenge();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
