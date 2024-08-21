import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PageAProposFormService } from './page-a-propos-form.service';
import { PageAProposService } from '../service/page-a-propos.service';
import { IPageAPropos } from '../page-a-propos.model';
import { IMembreEquipe } from 'app/entities/membre-equipe/membre-equipe.model';
import { MembreEquipeService } from 'app/entities/membre-equipe/service/membre-equipe.service';

import { PageAProposUpdateComponent } from './page-a-propos-update.component';

describe('PageAPropos Management Update Component', () => {
  let comp: PageAProposUpdateComponent;
  let fixture: ComponentFixture<PageAProposUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pageAProposFormService: PageAProposFormService;
  let pageAProposService: PageAProposService;
  let membreEquipeService: MembreEquipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PageAProposUpdateComponent],
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
      .overrideTemplate(PageAProposUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageAProposUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pageAProposFormService = TestBed.inject(PageAProposFormService);
    pageAProposService = TestBed.inject(PageAProposService);
    membreEquipeService = TestBed.inject(MembreEquipeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MembreEquipe query and add missing value', () => {
      const pageAPropos: IPageAPropos = { id: 456 };
      const membreEquipe1: IMembreEquipe = { id: 111 };
      pageAPropos.membreEquipe1 = membreEquipe1;

      const membreEquipeCollection: IMembreEquipe[] = [{ id: 63639 }];
      jest.spyOn(membreEquipeService, 'query').mockReturnValue(of(new HttpResponse({ body: membreEquipeCollection })));
      const additionalMembreEquipes = [membreEquipe1];
      const expectedCollection: IMembreEquipe[] = [...additionalMembreEquipes, ...membreEquipeCollection];
      jest.spyOn(membreEquipeService, 'addMembreEquipeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pageAPropos });
      comp.ngOnInit();

      expect(membreEquipeService.query).toHaveBeenCalled();
      expect(membreEquipeService.addMembreEquipeToCollectionIfMissing).toHaveBeenCalledWith(
        membreEquipeCollection,
        ...additionalMembreEquipes.map(expect.objectContaining)
      );
      expect(comp.membreEquipesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pageAPropos: IPageAPropos = { id: 456 };
      const membreEquipe1: IMembreEquipe = { id: 36168 };
      pageAPropos.membreEquipe1 = membreEquipe1;

      activatedRoute.data = of({ pageAPropos });
      comp.ngOnInit();

      expect(comp.membreEquipesSharedCollection).toContain(membreEquipe1);
      expect(comp.pageAPropos).toEqual(pageAPropos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageAPropos>>();
      const pageAPropos = { id: 123 };
      jest.spyOn(pageAProposFormService, 'getPageAPropos').mockReturnValue(pageAPropos);
      jest.spyOn(pageAProposService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageAPropos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageAPropos }));
      saveSubject.complete();

      // THEN
      expect(pageAProposFormService.getPageAPropos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pageAProposService.update).toHaveBeenCalledWith(expect.objectContaining(pageAPropos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageAPropos>>();
      const pageAPropos = { id: 123 };
      jest.spyOn(pageAProposFormService, 'getPageAPropos').mockReturnValue({ id: null });
      jest.spyOn(pageAProposService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageAPropos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageAPropos }));
      saveSubject.complete();

      // THEN
      expect(pageAProposFormService.getPageAPropos).toHaveBeenCalled();
      expect(pageAProposService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageAPropos>>();
      const pageAPropos = { id: 123 };
      jest.spyOn(pageAProposService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageAPropos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pageAProposService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMembreEquipe', () => {
      it('Should forward to membreEquipeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(membreEquipeService, 'compareMembreEquipe');
        comp.compareMembreEquipe(entity, entity2);
        expect(membreEquipeService.compareMembreEquipe).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
