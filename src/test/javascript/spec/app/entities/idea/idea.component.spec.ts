import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WirvsvirusTestModule } from '../../../test.module';
import { IdeaComponent } from 'app/entities/idea/idea.component';
import { IdeaService } from 'app/entities/idea/idea.service';
import { Idea } from 'app/shared/model/idea.model';

describe('Component Tests', () => {
  describe('Idea Management Component', () => {
    let comp: IdeaComponent;
    let fixture: ComponentFixture<IdeaComponent>;
    let service: IdeaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WirvsvirusTestModule],
        declarations: [IdeaComponent]
      })
        .overrideTemplate(IdeaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdeaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdeaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Idea(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ideas && comp.ideas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
