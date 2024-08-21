import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PageEquipeDetailComponent } from './page-equipe-detail.component';

describe('PageEquipe Management Detail Component', () => {
  let comp: PageEquipeDetailComponent;
  let fixture: ComponentFixture<PageEquipeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PageEquipeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pageEquipe: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PageEquipeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PageEquipeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pageEquipe on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pageEquipe).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
