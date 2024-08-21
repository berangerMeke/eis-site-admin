import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PageFormationDetailComponent } from './page-formation-detail.component';

describe('PageFormation Management Detail Component', () => {
  let comp: PageFormationDetailComponent;
  let fixture: ComponentFixture<PageFormationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PageFormationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pageFormation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PageFormationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PageFormationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pageFormation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pageFormation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
