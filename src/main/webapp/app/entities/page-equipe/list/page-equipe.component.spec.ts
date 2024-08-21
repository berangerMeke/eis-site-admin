import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PageEquipeService } from '../service/page-equipe.service';

import { PageEquipeComponent } from './page-equipe.component';

describe('PageEquipe Management Component', () => {
  let comp: PageEquipeComponent;
  let fixture: ComponentFixture<PageEquipeComponent>;
  let service: PageEquipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'page-equipe', component: PageEquipeComponent }]), HttpClientTestingModule],
      declarations: [PageEquipeComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PageEquipeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageEquipeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PageEquipeService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.pageEquipes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to pageEquipeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPageEquipeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPageEquipeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
