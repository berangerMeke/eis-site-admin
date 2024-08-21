import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PageEquipeFormService } from './page-equipe-form.service';
import { PageEquipeService } from '../service/page-equipe.service';
import { IPageEquipe } from '../page-equipe.model';

import { PageEquipeUpdateComponent } from './page-equipe-update.component';

describe('PageEquipe Management Update Component', () => {
  let comp: PageEquipeUpdateComponent;
  let fixture: ComponentFixture<PageEquipeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pageEquipeFormService: PageEquipeFormService;
  let pageEquipeService: PageEquipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PageEquipeUpdateComponent],
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
      .overrideTemplate(PageEquipeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageEquipeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pageEquipeFormService = TestBed.inject(PageEquipeFormService);
    pageEquipeService = TestBed.inject(PageEquipeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pageEquipe: IPageEquipe = { id: 456 };

      activatedRoute.data = of({ pageEquipe });
      comp.ngOnInit();

      expect(comp.pageEquipe).toEqual(pageEquipe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageEquipe>>();
      const pageEquipe = { id: 123 };
      jest.spyOn(pageEquipeFormService, 'getPageEquipe').mockReturnValue(pageEquipe);
      jest.spyOn(pageEquipeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageEquipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageEquipe }));
      saveSubject.complete();

      // THEN
      expect(pageEquipeFormService.getPageEquipe).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pageEquipeService.update).toHaveBeenCalledWith(expect.objectContaining(pageEquipe));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageEquipe>>();
      const pageEquipe = { id: 123 };
      jest.spyOn(pageEquipeFormService, 'getPageEquipe').mockReturnValue({ id: null });
      jest.spyOn(pageEquipeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageEquipe: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageEquipe }));
      saveSubject.complete();

      // THEN
      expect(pageEquipeFormService.getPageEquipe).toHaveBeenCalled();
      expect(pageEquipeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageEquipe>>();
      const pageEquipe = { id: 123 };
      jest.spyOn(pageEquipeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageEquipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pageEquipeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
