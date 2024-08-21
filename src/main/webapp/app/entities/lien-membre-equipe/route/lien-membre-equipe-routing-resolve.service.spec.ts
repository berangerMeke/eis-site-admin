import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILienMembreEquipe } from '../lien-membre-equipe.model';
import { LienMembreEquipeService } from '../service/lien-membre-equipe.service';

import { LienMembreEquipeRoutingResolveService } from './lien-membre-equipe-routing-resolve.service';

describe('LienMembreEquipe routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LienMembreEquipeRoutingResolveService;
  let service: LienMembreEquipeService;
  let resultLienMembreEquipe: ILienMembreEquipe | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(LienMembreEquipeRoutingResolveService);
    service = TestBed.inject(LienMembreEquipeService);
    resultLienMembreEquipe = undefined;
  });

  describe('resolve', () => {
    it('should return ILienMembreEquipe returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLienMembreEquipe = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLienMembreEquipe).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLienMembreEquipe = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLienMembreEquipe).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ILienMembreEquipe>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLienMembreEquipe = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLienMembreEquipe).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
