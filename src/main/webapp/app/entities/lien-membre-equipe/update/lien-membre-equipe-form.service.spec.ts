import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../lien-membre-equipe.test-samples';

import { LienMembreEquipeFormService } from './lien-membre-equipe-form.service';

describe('LienMembreEquipe Form Service', () => {
  let service: LienMembreEquipeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LienMembreEquipeFormService);
  });

  describe('Service methods', () => {
    describe('createLienMembreEquipeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLienMembreEquipeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            facebook: expect.any(Object),
            whatsapp: expect.any(Object),
            linkedin: expect.any(Object),
            twitter: expect.any(Object),
          })
        );
      });

      it('passing ILienMembreEquipe should create a new form with FormGroup', () => {
        const formGroup = service.createLienMembreEquipeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            facebook: expect.any(Object),
            whatsapp: expect.any(Object),
            linkedin: expect.any(Object),
            twitter: expect.any(Object),
          })
        );
      });
    });

    describe('getLienMembreEquipe', () => {
      it('should return NewLienMembreEquipe for default LienMembreEquipe initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLienMembreEquipeFormGroup(sampleWithNewData);

        const lienMembreEquipe = service.getLienMembreEquipe(formGroup) as any;

        expect(lienMembreEquipe).toMatchObject(sampleWithNewData);
      });

      it('should return NewLienMembreEquipe for empty LienMembreEquipe initial value', () => {
        const formGroup = service.createLienMembreEquipeFormGroup();

        const lienMembreEquipe = service.getLienMembreEquipe(formGroup) as any;

        expect(lienMembreEquipe).toMatchObject({});
      });

      it('should return ILienMembreEquipe', () => {
        const formGroup = service.createLienMembreEquipeFormGroup(sampleWithRequiredData);

        const lienMembreEquipe = service.getLienMembreEquipe(formGroup) as any;

        expect(lienMembreEquipe).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILienMembreEquipe should not enable id FormControl', () => {
        const formGroup = service.createLienMembreEquipeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLienMembreEquipe should disable id FormControl', () => {
        const formGroup = service.createLienMembreEquipeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
