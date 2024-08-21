import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PartenairesService } from '../service/partenaires.service';

import { PartenairesComponent } from './partenaires.component';

describe('Partenaires Management Component', () => {
  let comp: PartenairesComponent;
  let fixture: ComponentFixture<PartenairesComponent>;
  let service: PartenairesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'partenaires', component: PartenairesComponent }]), HttpClientTestingModule],
      declarations: [PartenairesComponent],
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
      .overrideTemplate(PartenairesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartenairesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartenairesService);

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
    expect(comp.partenaires?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to partenairesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPartenairesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPartenairesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
