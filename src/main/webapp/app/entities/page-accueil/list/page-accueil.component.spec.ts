import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PageAccueilService } from '../service/page-accueil.service';

import { PageAccueilComponent } from './page-accueil.component';

describe('PageAccueil Management Component', () => {
  let comp: PageAccueilComponent;
  let fixture: ComponentFixture<PageAccueilComponent>;
  let service: PageAccueilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'page-accueil', component: PageAccueilComponent }]), HttpClientTestingModule],
      declarations: [PageAccueilComponent],
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
      .overrideTemplate(PageAccueilComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageAccueilComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PageAccueilService);

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
    expect(comp.pageAccueils?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to pageAccueilService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPageAccueilIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPageAccueilIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
