import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../page-formation.test-samples';

import { PageFormationFormService } from './page-formation-form.service';

describe('PageFormation Form Service', () => {
  let service: PageFormationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PageFormationFormService);
  });

  describe('Service methods', () => {
    describe('createPageFormationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPageFormationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titre: expect.any(Object),
            sousTitre: expect.any(Object),
          })
        );
      });

      it('passing IPageFormation should create a new form with FormGroup', () => {
        const formGroup = service.createPageFormationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titre: expect.any(Object),
            sousTitre: expect.any(Object),
          })
        );
      });
    });

    describe('getPageFormation', () => {
      it('should return NewPageFormation for default PageFormation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPageFormationFormGroup(sampleWithNewData);

        const pageFormation = service.getPageFormation(formGroup) as any;

        expect(pageFormation).toMatchObject(sampleWithNewData);
      });

      it('should return NewPageFormation for empty PageFormation initial value', () => {
        const formGroup = service.createPageFormationFormGroup();

        const pageFormation = service.getPageFormation(formGroup) as any;

        expect(pageFormation).toMatchObject({});
      });

      it('should return IPageFormation', () => {
        const formGroup = service.createPageFormationFormGroup(sampleWithRequiredData);

        const pageFormation = service.getPageFormation(formGroup) as any;

        expect(pageFormation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPageFormation should not enable id FormControl', () => {
        const formGroup = service.createPageFormationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPageFormation should disable id FormControl', () => {
        const formGroup = service.createPageFormationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
