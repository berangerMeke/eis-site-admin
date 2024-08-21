import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nav-bar.test-samples';

import { NavBarFormService } from './nav-bar-form.service';

describe('NavBar Form Service', () => {
  let service: NavBarFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NavBarFormService);
  });

  describe('Service methods', () => {
    describe('createNavBarFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNavBarFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            logo: expect.any(Object),
            menu1: expect.any(Object),
            menu2: expect.any(Object),
            menu3: expect.any(Object),
            menu4: expect.any(Object),
            menu5: expect.any(Object),
            menu6: expect.any(Object),
          })
        );
      });

      it('passing INavBar should create a new form with FormGroup', () => {
        const formGroup = service.createNavBarFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            logo: expect.any(Object),
            menu1: expect.any(Object),
            menu2: expect.any(Object),
            menu3: expect.any(Object),
            menu4: expect.any(Object),
            menu5: expect.any(Object),
            menu6: expect.any(Object),
          })
        );
      });
    });

    describe('getNavBar', () => {
      it('should return NewNavBar for default NavBar initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNavBarFormGroup(sampleWithNewData);

        const navBar = service.getNavBar(formGroup) as any;

        expect(navBar).toMatchObject(sampleWithNewData);
      });

      it('should return NewNavBar for empty NavBar initial value', () => {
        const formGroup = service.createNavBarFormGroup();

        const navBar = service.getNavBar(formGroup) as any;

        expect(navBar).toMatchObject({});
      });

      it('should return INavBar', () => {
        const formGroup = service.createNavBarFormGroup(sampleWithRequiredData);

        const navBar = service.getNavBar(formGroup) as any;

        expect(navBar).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INavBar should not enable id FormControl', () => {
        const formGroup = service.createNavBarFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNavBar should disable id FormControl', () => {
        const formGroup = service.createNavBarFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
