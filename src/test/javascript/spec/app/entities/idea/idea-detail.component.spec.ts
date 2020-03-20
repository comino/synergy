import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WirvsvirusTestModule } from '../../../test.module';
import { IdeaDetailComponent } from 'app/entities/idea/idea-detail.component';
import { Idea } from 'app/shared/model/idea.model';

describe('Component Tests', () => {
  describe('Idea Management Detail Component', () => {
    let comp: IdeaDetailComponent;
    let fixture: ComponentFixture<IdeaDetailComponent>;
    const route = ({ data: of({ idea: new Idea(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WirvsvirusTestModule],
        declarations: [IdeaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IdeaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdeaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load idea on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.idea).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
