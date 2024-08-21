import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PageServiceDetailComponent } from './page-service-detail.component';

describe('PageService Management Detail Component', () => {
  let comp: PageServiceDetailComponent;
  let fixture: ComponentFixture<PageServiceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PageServiceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pageService: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PageServiceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PageServiceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pageService on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pageService).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
