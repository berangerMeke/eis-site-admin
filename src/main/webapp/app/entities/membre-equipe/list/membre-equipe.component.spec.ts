import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MembreEquipeService } from '../service/membre-equipe.service';

import { MembreEquipeComponent } from './membre-equipe.component';

describe('MembreEquipe Management Component', () => {
  let comp: MembreEquipeComponent;
  let fixture: ComponentFixture<MembreEquipeComponent>;
  let service: MembreEquipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'membre-equipe', component: MembreEquipeComponent }]), HttpClientTestingModule],
      declarations: [MembreEquipeComponent],
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
      .overrideTemplate(MembreEquipeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MembreEquipeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MembreEquipeService);

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
    expect(comp.membreEquipes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to membreEquipeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMembreEquipeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMembreEquipeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
