import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PageServiceFormService } from './page-service-form.service';
import { PageServiceService } from '../service/page-service.service';
import { IPageService } from '../page-service.model';

import { PageServiceUpdateComponent } from './page-service-update.component';

describe('PageService Management Update Component', () => {
  let comp: PageServiceUpdateComponent;
  let fixture: ComponentFixture<PageServiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pageServiceFormService: PageServiceFormService;
  let pageServiceService: PageServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PageServiceUpdateComponent],
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
      .overrideTemplate(PageServiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PageServiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pageServiceFormService = TestBed.inject(PageServiceFormService);
    pageServiceService = TestBed.inject(PageServiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pageService: IPageService = { id: 456 };

      activatedRoute.data = of({ pageService });
      comp.ngOnInit();

      expect(comp.pageService).toEqual(pageService);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageService>>();
      const pageService = { id: 123 };
      jest.spyOn(pageServiceFormService, 'getPageService').mockReturnValue(pageService);
      jest.spyOn(pageServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageService }));
      saveSubject.complete();

      // THEN
      expect(pageServiceFormService.getPageService).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pageServiceService.update).toHaveBeenCalledWith(expect.objectContaining(pageService));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageService>>();
      const pageService = { id: 123 };
      jest.spyOn(pageServiceFormService, 'getPageService').mockReturnValue({ id: null });
      jest.spyOn(pageServiceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageService: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pageService }));
      saveSubject.complete();

      // THEN
      expect(pageServiceFormService.getPageService).toHaveBeenCalled();
      expect(pageServiceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPageService>>();
      const pageService = { id: 123 };
      jest.spyOn(pageServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pageService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pageServiceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
