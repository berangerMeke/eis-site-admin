import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NavBarFormService } from './nav-bar-form.service';
import { NavBarService } from '../service/nav-bar.service';
import { INavBar } from '../nav-bar.model';

import { NavBarUpdateComponent } from './nav-bar-update.component';

describe('NavBar Management Update Component', () => {
  let comp: NavBarUpdateComponent;
  let fixture: ComponentFixture<NavBarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let navBarFormService: NavBarFormService;
  let navBarService: NavBarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NavBarUpdateComponent],
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
      .overrideTemplate(NavBarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NavBarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    navBarFormService = TestBed.inject(NavBarFormService);
    navBarService = TestBed.inject(NavBarService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const navBar: INavBar = { id: 456 };

      activatedRoute.data = of({ navBar });
      comp.ngOnInit();

      expect(comp.navBar).toEqual(navBar);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INavBar>>();
      const navBar = { id: 123 };
      jest.spyOn(navBarFormService, 'getNavBar').mockReturnValue(navBar);
      jest.spyOn(navBarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ navBar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: navBar }));
      saveSubject.complete();

      // THEN
      expect(navBarFormService.getNavBar).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(navBarService.update).toHaveBeenCalledWith(expect.objectContaining(navBar));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INavBar>>();
      const navBar = { id: 123 };
      jest.spyOn(navBarFormService, 'getNavBar').mockReturnValue({ id: null });
      jest.spyOn(navBarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ navBar: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: navBar }));
      saveSubject.complete();

      // THEN
      expect(navBarFormService.getNavBar).toHaveBeenCalled();
      expect(navBarService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INavBar>>();
      const navBar = { id: 123 };
      jest.spyOn(navBarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ navBar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(navBarService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
