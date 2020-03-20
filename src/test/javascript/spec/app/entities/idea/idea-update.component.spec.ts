import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WirvsvirusTestModule } from '../../../test.module';
import { IdeaUpdateComponent } from 'app/entities/idea/idea-update.component';
import { IdeaService } from 'app/entities/idea/idea.service';
import { Idea } from 'app/shared/model/idea.model';

describe('Component Tests', () => {
  describe('Idea Management Update Component', () => {
    let comp: IdeaUpdateComponent;
    let fixture: ComponentFixture<IdeaUpdateComponent>;
    let service: IdeaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WirvsvirusTestModule],
        declarations: [IdeaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IdeaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdeaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdeaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Idea(123);
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
        const entity = new Idea();
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
