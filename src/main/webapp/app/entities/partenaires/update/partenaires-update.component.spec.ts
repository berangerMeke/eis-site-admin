import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartenairesFormService } from './partenaires-form.service';
import { PartenairesService } from '../service/partenaires.service';
import { IPartenaires } from '../partenaires.model';

import { PartenairesUpdateComponent } from './partenaires-update.component';

describe('Partenaires Management Update Component', () => {
  let comp: PartenairesUpdateComponent;
  let fixture: ComponentFixture<PartenairesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partenairesFormService: PartenairesFormService;
  let partenairesService: PartenairesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartenairesUpdateComponent],
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
      .overrideTemplate(PartenairesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartenairesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partenairesFormService = TestBed.inject(PartenairesFormService);
    partenairesService = TestBed.inject(PartenairesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const partenaires: IPartenaires = { id: 456 };

      activatedRoute.data = of({ partenaires });
      comp.ngOnInit();

      expect(comp.partenaires).toEqual(partenaires);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartenaires>>();
      const partenaires = { id: 123 };
      jest.spyOn(partenairesFormService, 'getPartenaires').mockReturnValue(partenaires);
      jest.spyOn(partenairesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partenaires });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partenaires }));
      saveSubject.complete();

      // THEN
      expect(partenairesFormService.getPartenaires).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(partenairesService.update).toHaveBeenCalledWith(expect.objectContaining(partenaires));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartenaires>>();
      const partenaires = { id: 123 };
      jest.spyOn(partenairesFormService, 'getPartenaires').mockReturnValue({ id: null });
      jest.spyOn(partenairesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partenaires: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partenaires }));
      saveSubject.complete();

      // THEN
      expect(partenairesFormService.getPartenaires).toHaveBeenCalled();
      expect(partenairesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartenaires>>();
      const partenaires = { id: 123 };
      jest.spyOn(partenairesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partenaires });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partenairesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
