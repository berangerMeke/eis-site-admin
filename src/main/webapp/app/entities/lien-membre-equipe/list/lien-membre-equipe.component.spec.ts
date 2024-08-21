import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LienMembreEquipeService } from '../service/lien-membre-equipe.service';

import { LienMembreEquipeComponent } from './lien-membre-equipe.component';

describe('LienMembreEquipe Management Component', () => {
  let comp: LienMembreEquipeComponent;
  let fixture: ComponentFixture<LienMembreEquipeComponent>;
  let service: LienMembreEquipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'lien-membre-equipe', component: LienMembreEquipeComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [LienMembreEquipeComponent],
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
      .overrideTemplate(LienMembreEquipeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LienMembreEquipeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LienMembreEquipeService);

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
    expect(comp.lienMembreEquipes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to lienMembreEquipeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getLienMembreEquipeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getLienMembreEquipeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
