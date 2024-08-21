import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LienMembreEquipeFormService } from './lien-membre-equipe-form.service';
import { LienMembreEquipeService } from '../service/lien-membre-equipe.service';
import { ILienMembreEquipe } from '../lien-membre-equipe.model';

import { LienMembreEquipeUpdateComponent } from './lien-membre-equipe-update.component';

describe('LienMembreEquipe Management Update Component', () => {
  let comp: LienMembreEquipeUpdateComponent;
  let fixture: ComponentFixture<LienMembreEquipeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let lienMembreEquipeFormService: LienMembreEquipeFormService;
  let lienMembreEquipeService: LienMembreEquipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LienMembreEquipeUpdateComponent],
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
      .overrideTemplate(LienMembreEquipeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LienMembreEquipeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    lienMembreEquipeFormService = TestBed.inject(LienMembreEquipeFormService);
    lienMembreEquipeService = TestBed.inject(LienMembreEquipeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const lienMembreEquipe: ILienMembreEquipe = { id: 456 };

      activatedRoute.data = of({ lienMembreEquipe });
      comp.ngOnInit();

      expect(comp.lienMembreEquipe).toEqual(lienMembreEquipe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILienMembreEquipe>>();
      const lienMembreEquipe = { id: 123 };
      jest.spyOn(lienMembreEquipeFormService, 'getLienMembreEquipe').mockReturnValue(lienMembreEquipe);
      jest.spyOn(lienMembreEquipeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lienMembreEquipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lienMembreEquipe }));
      saveSubject.complete();

      // THEN
      expect(lienMembreEquipeFormService.getLienMembreEquipe).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(lienMembreEquipeService.update).toHaveBeenCalledWith(expect.objectContaining(lienMembreEquipe));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILienMembreEquipe>>();
      const lienMembreEquipe = { id: 123 };
      jest.spyOn(lienMembreEquipeFormService, 'getLienMembreEquipe').mockReturnValue({ id: null });
      jest.spyOn(lienMembreEquipeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lienMembreEquipe: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lienMembreEquipe }));
      saveSubject.complete();

      // THEN
      expect(lienMembreEquipeFormService.getLienMembreEquipe).toHaveBeenCalled();
      expect(lienMembreEquipeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILienMembreEquipe>>();
      const lienMembreEquipe = { id: 123 };
      jest.spyOn(lienMembreEquipeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lienMembreEquipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(lienMembreEquipeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
