import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MembreEquipeFormService } from './membre-equipe-form.service';
import { MembreEquipeService } from '../service/membre-equipe.service';
import { IMembreEquipe } from '../membre-equipe.model';
import { IPageEquipe } from 'app/entities/page-equipe/page-equipe.model';
import { PageEquipeService } from 'app/entities/page-equipe/service/page-equipe.service';

import { MembreEquipeUpdateComponent } from './membre-equipe-update.component';

describe('MembreEquipe Management Update Component', () => {
  let comp: MembreEquipeUpdateComponent;
  let fixture: ComponentFixture<MembreEquipeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let membreEquipeFormService: MembreEquipeFormService;
  let membreEquipeService: MembreEquipeService;
  let pageEquipeService: PageEquipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MembreEquipeUpdateComponent],
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
      .overrideTemplate(MembreEquipeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MembreEquipeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    membreEquipeFormService = TestBed.inject(MembreEquipeFormService);
    membreEquipeService = TestBed.inject(MembreEquipeService);
    pageEquipeService = TestBed.inject(PageEquipeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PageEquipe query and add missing value', () => {
      const membreEquipe: IMembreEquipe = { id: 456 };
      const pageEquipe1: IPageEquipe = { id: 90476 };
      membreEquipe.pageEquipe1 = pageEquipe1;

      const pageEquipeCollection: IPageEquipe[] = [{ id: 80208 }];
      jest.spyOn(pageEquipeService, 'query').mockReturnValue(of(new HttpResponse({ body: pageEquipeCollection })));
      const additionalPageEquipes = [pageEquipe1];
      const expectedCollection: IPageEquipe[] = [...additionalPageEquipes, ...pageEquipeCollection];
      jest.spyOn(pageEquipeService, 'addPageEquipeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ membreEquipe });
      comp.ngOnInit();

      expect(pageEquipeService.query).toHaveBeenCalled();
      expect(pageEquipeService.addPageEquipeToCollectionIfMissing).toHaveBeenCalledWith(
        pageEquipeCollection,
        ...additionalPageEquipes.map(expect.objectContaining)
      );
      expect(comp.pageEquipesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const membreEquipe: IMembreEquipe = { id: 456 };
      const pageEquipe1: IPageEquipe = { id: 51407 };
      membreEquipe.pageEquipe1 = pageEquipe1;

      activatedRoute.data = of({ membreEquipe });
      comp.ngOnInit();

      expect(comp.pageEquipesSharedCollection).toContain(pageEquipe1);
      expect(comp.membreEquipe).toEqual(membreEquipe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembreEquipe>>();
      const membreEquipe = { id: 123 };
      jest.spyOn(membreEquipeFormService, 'getMembreEquipe').mockReturnValue(membreEquipe);
      jest.spyOn(membreEquipeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membreEquipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membreEquipe }));
      saveSubject.complete();

      // THEN
      expect(membreEquipeFormService.getMembreEquipe).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(membreEquipeService.update).toHaveBeenCalledWith(expect.objectContaining(membreEquipe));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembreEquipe>>();
      const membreEquipe = { id: 123 };
      jest.spyOn(membreEquipeFormService, 'getMembreEquipe').mockReturnValue({ id: null });
      jest.spyOn(membreEquipeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membreEquipe: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membreEquipe }));
      saveSubject.complete();

      // THEN
      expect(membreEquipeFormService.getMembreEquipe).toHaveBeenCalled();
      expect(membreEquipeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembreEquipe>>();
      const membreEquipe = { id: 123 };
      jest.spyOn(membreEquipeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membreEquipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(membreEquipeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePageEquipe', () => {
      it('Should forward to pageEquipeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pageEquipeService, 'comparePageEquipe');
        comp.comparePageEquipe(entity, entity2);
        expect(pageEquipeService.comparePageEquipe).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
