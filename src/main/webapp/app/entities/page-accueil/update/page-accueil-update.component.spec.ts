import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PageAccueilFormService } from './page-accueil-form.service';
import { PageAccueilService } from '../service/page-accueil.service';
import { IPageAccueil } from '../page-accueil.model';
import { IPartenaires } from 'app/entities/partenaires/partenaires.model';
import { PartenairesService } from 'app/entities/partenaires/service/partenaires.service';

import { PageAccueilUpdateComponent } from './page-accueil-update.component';

describe('PageAccueil Management Update Component', () => {
  let comp: PageAccueilUpdateComponent;
  let fixture: ComponentFixture<PageAccueilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pageAccueilFormService: PageAccueilFormService;
  let pageAccueilService: PageAccueilService;
  let partenairesService: PartenairesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PageAccueilUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PageAccueilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageAccueilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pageAccueilFormService = TestBed.inject(PageAccueilFormService);
    pageAccueilService = TestBed.inject(PageAccueilService);
    partenairesService = TestBed.inject(PartenairesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Partenaires query and add missing value', () => {
      const pageAccueil: IPageAccueil = { id: 456 };
      const partenaires1: IPartenaires = { id: 38380 };
      pageAccueil.partenaires1 = partenaires1;

      const partenairesCollection: IPartenaires[] = [{ id: 82707 }];
      jest.spyOn(partenairesService, 'query').mockReturnValue(of(new HttpResponse({ body: partenairesCollection })));
      const additionalPartenaires = [partenaires1];
      const expectedCollection: IPartenaires[] = [...additionalPartenaires, ...partenairesCollection];
      jest.spyOn(partenairesService, 'addPartenairesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pageAccueil });
      comp.ngOnInit();

      expect(partenairesService.query).toHaveBeenCalled();
      expect(partenairesService.addPartenairesToCollectionIfMissing).toHaveBeenCalledWith(
        partenairesCollection,
        ...additionalPartenaires.map(expect.objectContaining)
      );
      expect(comp.partenairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pageAccueil: IPageAccueil = { id: 456 };
      const partenaires1: IPartenaires = { id: 90949 };
      pageAccueil.partenaires1 = partenaires1;

      activatedRoute.data = of({ pageAccueil });
      comp.ngOnInit();

      expect(comp.partenairesSharedCollection).toContain(partenaires1);
      expect(comp.pageAccueil).toEqual(pageAccueil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageAccueil>>();
      const pageAccueil = { id: 123 };
      jest.spyOn(pageAccueilFormService, 'getPageAccueil').mockReturnValue(pageAccueil);
      jest.spyOn(pageAccueilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageAccueil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageAccueil }));
      saveSubject.complete();

      // THEN
      expect(pageAccueilFormService.getPageAccueil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pageAccueilService.update).toHaveBeenCalledWith(expect.objectContaining(pageAccueil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageAccueil>>();
      const pageAccueil = { id: 123 };
      jest.spyOn(pageAccueilFormService, 'getPageAccueil').mockReturnValue({ id: null });
      jest.spyOn(pageAccueilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageAccueil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageAccueil }));
      saveSubject.complete();

      // THEN
      expect(pageAccueilFormService.getPageAccueil).toHaveBeenCalled();
      expect(pageAccueilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageAccueil>>();
      const pageAccueil = { id: 123 };
      jest.spyOn(pageAccueilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageAccueil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pageAccueilService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePartenaires', () => {
      it('Should forward to partenairesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(partenairesService, 'comparePartenaires');
        comp.comparePartenaires(entity, entity2);
        expect(partenairesService.comparePartenaires).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
