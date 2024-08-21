import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PageServiceService } from '../service/page-service.service';

import { PageServiceComponent } from './page-service.component';

describe('PageService Management Component', () => {
  let comp: PageServiceComponent;
  let fixture: ComponentFixture<PageServiceComponent>;
  let service: PageServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'page-service', component: PageServiceComponent }]), HttpClientTestingModule],
      declarations: [PageServiceComponent],
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
      .overrideTemplate(PageServiceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageServiceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PageServiceService);

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
    expect(comp.pageServices?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to pageServiceService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPageServiceIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPageServiceIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
