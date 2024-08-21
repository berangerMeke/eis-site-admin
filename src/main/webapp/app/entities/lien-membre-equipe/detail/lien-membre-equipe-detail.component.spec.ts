import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LienMembreEquipeDetailComponent } from './lien-membre-equipe-detail.component';

describe('LienMembreEquipe Management Detail Component', () => {
  let comp: LienMembreEquipeDetailComponent;
  let fixture: ComponentFixture<LienMembreEquipeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LienMembreEquipeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ lienMembreEquipe: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LienMembreEquipeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LienMembreEquipeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load lienMembreEquipe on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.lienMembreEquipe).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
