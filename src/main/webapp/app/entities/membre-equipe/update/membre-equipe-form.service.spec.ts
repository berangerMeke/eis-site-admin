import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../membre-equipe.test-samples';

import { MembreEquipeFormService } from './membre-equipe-form.service';

describe('MembreEquipe Form Service', () => {
  let service: MembreEquipeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MembreEquipeFormService);
  });

  describe('Service methods', () => {
    describe('createMembreEquipeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMembreEquipeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            prenom: expect.any(Object),
            telephone: expect.any(Object),
            adresseMail: expect.any(Object),
            certification: expect.any(Object),
            diplome: expect.any(Object),
            niveauEtude: expect.any(Object),
            photo: expect.any(Object),
            description: expect.any(Object),
            message: expect.any(Object),
            fonction: expect.any(Object),
            pageEquipe1: expect.any(Object),
          })
        );
      });

      it('passing IMembreEquipe should create a new form with FormGroup', () => {
        const formGroup = service.createMembreEquipeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            prenom: expect.any(Object),
            telephone: expect.any(Object),
            adresseMail: expect.any(Object),
            certification: expect.any(Object),
            diplome: expect.any(Object),
            niveauEtude: expect.any(Object),
            photo: expect.any(Object),
            description: expect.any(Object),
            message: expect.any(Object),
            fonction: expect.any(Object),
            pageEquipe1: expect.any(Object),
          })
        );
      });
    });

    describe('getMembreEquipe', () => {
      it('should return NewMembreEquipe for default MembreEquipe initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMembreEquipeFormGroup(sampleWithNewData);

        const membreEquipe = service.getMembreEquipe(formGroup) as any;

        expect(membreEquipe).toMatchObject(sampleWithNewData);
      });

      it('should return NewMembreEquipe for empty MembreEquipe initial value', () => {
        const formGroup = service.createMembreEquipeFormGroup();

        const membreEquipe = service.getMembreEquipe(formGroup) as any;

        expect(membreEquipe).toMatchObject({});
      });

      it('should return IMembreEquipe', () => {
        const formGroup = service.createMembreEquipeFormGroup(sampleWithRequiredData);

        const membreEquipe = service.getMembreEquipe(formGroup) as any;

        expect(membreEquipe).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMembreEquipe should not enable id FormControl', () => {
        const formGroup = service.createMembreEquipeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMembreEquipe should disable id FormControl', () => {
        const formGroup = service.createMembreEquipeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
