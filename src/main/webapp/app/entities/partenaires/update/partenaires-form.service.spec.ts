import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../partenaires.test-samples';

import { PartenairesFormService } from './partenaires-form.service';

describe('Partenaires Form Service', () => {
  let service: PartenairesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PartenairesFormService);
  });

  describe('Service methods', () => {
    describe('createPartenairesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPartenairesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            photo: expect.any(Object),
            description: expect.any(Object),
            lien: expect.any(Object),
          })
        );
      });

      it('passing IPartenaires should create a new form with FormGroup', () => {
        const formGroup = service.createPartenairesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            photo: expect.any(Object),
            description: expect.any(Object),
            lien: expect.any(Object),
          })
        );
      });
    });

    describe('getPartenaires', () => {
      it('should return NewPartenaires for default Partenaires initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPartenairesFormGroup(sampleWithNewData);

        const partenaires = service.getPartenaires(formGroup) as any;

        expect(partenaires).toMatchObject(sampleWithNewData);
      });

      it('should return NewPartenaires for empty Partenaires initial value', () => {
        const formGroup = service.createPartenairesFormGroup();

        const partenaires = service.getPartenaires(formGroup) as any;

        expect(partenaires).toMatchObject({});
      });

      it('should return IPartenaires', () => {
        const formGroup = service.createPartenairesFormGroup(sampleWithRequiredData);

        const partenaires = service.getPartenaires(formGroup) as any;

        expect(partenaires).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPartenaires should not enable id FormControl', () => {
        const formGroup = service.createPartenairesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPartenaires should disable id FormControl', () => {
        const formGroup = service.createPartenairesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
