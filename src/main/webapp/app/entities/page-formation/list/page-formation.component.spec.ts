import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PageFormationService } from '../service/page-formation.service';

import { PageFormationComponent } from './page-formation.component';

describe('PageFormation Management Component', () => {
  let comp: PageFormationComponent;
  let fixture: ComponentFixture<PageFormationComponent>;
  let service: PageFormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'page-formation', component: PageFormationComponent }]), HttpClientTestingModule],
      declarations: [PageFormationComponent],
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
      .overrideTemplate(PageFormationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageFormationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PageFormationService);

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
    expect(comp.pageFormations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to pageFormationService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPageFormationIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPageFormationIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
