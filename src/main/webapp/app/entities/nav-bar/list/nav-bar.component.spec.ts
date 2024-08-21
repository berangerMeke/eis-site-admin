import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NavBarService } from '../service/nav-bar.service';

import { NavBarComponent } from './nav-bar.component';

describe('NavBar Management Component', () => {
  let comp: NavBarComponent;
  let fixture: ComponentFixture<NavBarComponent>;
  let service: NavBarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'nav-bar', component: NavBarComponent }]), HttpClientTestingModule],
      declarations: [NavBarComponent],
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
      .overrideTemplate(NavBarComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NavBarComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NavBarService);

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
    expect(comp.navBars?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to navBarService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getNavBarIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getNavBarIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
