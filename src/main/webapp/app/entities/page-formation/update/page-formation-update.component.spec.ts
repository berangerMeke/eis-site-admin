import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PageFormationFormService } from './page-formation-form.service';
import { PageFormationService } from '../service/page-formation.service';
import { IPageFormation } from '../page-formation.model';

import { PageFormationUpdateComponent } from './page-formation-update.component';

describe('PageFormation Management Update Component', () => {
  let comp: PageFormationUpdateComponent;
  let fixture: ComponentFixture<PageFormationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pageFormationFormService: PageFormationFormService;
  let pageFormationService: PageFormationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PageFormationUpdateComponent],
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
      .overrideTemplate(PageFormationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageFormationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pageFormationFormService = TestBed.inject(PageFormationFormService);
    pageFormationService = TestBed.inject(PageFormationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pageFormation: IPageFormation = { id: 456 };

      activatedRoute.data = of({ pageFormation });
      comp.ngOnInit();

      expect(comp.pageFormation).toEqual(pageFormation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageFormation>>();
      const pageFormation = { id: 123 };
      jest.spyOn(pageFormationFormService, 'getPageFormation').mockReturnValue(pageFormation);
      jest.spyOn(pageFormationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageFormation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageFormation }));
      saveSubject.complete();

      // THEN
      expect(pageFormationFormService.getPageFormation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pageFormationService.update).toHaveBeenCalledWith(expect.objectContaining(pageFormation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageFormation>>();
      const pageFormation = { id: 123 };
      jest.spyOn(pageFormationFormService, 'getPageFormation').mockReturnValue({ id: null });
      jest.spyOn(pageFormationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageFormation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageFormation }));
      saveSubject.complete();

      // THEN
      expect(pageFormationFormService.getPageFormation).toHaveBeenCalled();
      expect(pageFormationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageFormation>>();
      const pageFormation = { id: 123 };
      jest.spyOn(pageFormationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageFormation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pageFormationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
