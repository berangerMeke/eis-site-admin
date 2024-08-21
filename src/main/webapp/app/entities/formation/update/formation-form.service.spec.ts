import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../formation.test-samples';

import { FormationFormService } from './formation-form.service';

describe('Formation Form Service', () => {
  let service: FormationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormationFormService);
  });

  describe('Service methods', () => {
    describe('createFormationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFormationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            photo: expect.any(Object),
            texte: expect.any(Object),
            cout: expect.any(Object),
            duree: expect.any(Object),
            categorie: expect.any(Object),
            lien: expect.any(Object),
          })
        );
      });

      it('passing IFormation should create a new form with FormGroup', () => {
        const formGroup = service.createFormationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            photo: expect.any(Object),
            texte: expect.any(Object),
            cout: expect.any(Object),
            duree: expect.any(Object),
            categorie: expect.any(Object),
            lien: expect.any(Object),
          })
        );
      });
    });

    describe('getFormation', () => {
      it('should return NewFormation for default Formation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFormationFormGroup(sampleWithNewData);

        const formation = service.getFormation(formGroup) as any;

        expect(formation).toMatchObject(sampleWithNewData);
      });

      it('should return NewFormation for empty Formation initial value', () => {
        const formGroup = service.createFormationFormGroup();

        const formation = service.getFormation(formGroup) as any;

        expect(formation).toMatchObject({});
      });

      it('should return IFormation', () => {
        const formGroup = service.createFormationFormGroup(sampleWithRequiredData);

        const formation = service.getFormation(formGroup) as any;

        expect(formation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFormation should not enable id FormControl', () => {
        const formGroup = service.createFormationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFormation should disable id FormControl', () => {
        const formGroup = service.createFormationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
