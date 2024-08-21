import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ServiceService } from '../service/service.service';

import { ServiceComponent } from './service.component';

describe('Service Management Component', () => {
  let comp: ServiceComponent;
  let fixture: ComponentFixture<ServiceComponent>;
  let service: ServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'service', component: ServiceComponent }]), HttpClientTestingModule],
      declarations: [ServiceComponent],
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
      .overrideTemplate(ServiceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServiceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ServiceService);

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
    expect(comp.services?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to serviceService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getServiceIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getServiceIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
