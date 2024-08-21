import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PageAProposService } from '../service/page-a-propos.service';

import { PageAProposComponent } from './page-a-propos.component';

describe('PageAPropos Management Component', () => {
  let comp: PageAProposComponent;
  let fixture: ComponentFixture<PageAProposComponent>;
  let service: PageAProposService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'page-a-propos', component: PageAProposComponent }]), HttpClientTestingModule],
      declarations: [PageAProposComponent],
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
      .overrideTemplate(PageAProposComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageAProposComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PageAProposService);

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
    expect(comp.pageAPropos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to pageAProposService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPageAProposIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPageAProposIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
