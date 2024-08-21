import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../page-equipe.test-samples';

import { PageEquipeFormService } from './page-equipe-form.service';

describe('PageEquipe Form Service', () => {
  let service: PageEquipeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PageEquipeFormService);
  });

  describe('Service methods', () => {
    describe('createPageEquipeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPageEquipeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sec1Img: expect.any(Object),
            sec1Titre: expect.any(Object),
            sec1Desc: expect.any(Object),
          })
        );
      });

      it('passing IPageEquipe should create a new form with FormGroup', () => {
        const formGroup = service.createPageEquipeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sec1Img: expect.any(Object),
            sec1Titre: expect.any(Object),
            sec1Desc: expect.any(Object),
          })
        );
      });
    });

    describe('getPageEquipe', () => {
      it('should return NewPageEquipe for default PageEquipe initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPageEquipeFormGroup(sampleWithNewData);

        const pageEquipe = service.getPageEquipe(formGroup) as any;

        expect(pageEquipe).toMatchObject(sampleWithNewData);
      });

      it('should return NewPageEquipe for empty PageEquipe initial value', () => {
        const formGroup = service.createPageEquipeFormGroup();

        const pageEquipe = service.getPageEquipe(formGroup) as any;

        expect(pageEquipe).toMatchObject({});
      });

      it('should return IPageEquipe', () => {
        const formGroup = service.createPageEquipeFormGroup(sampleWithRequiredData);

        const pageEquipe = service.getPageEquipe(formGroup) as any;

        expect(pageEquipe).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPageEquipe should not enable id FormControl', () => {
        const formGroup = service.createPageEquipeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPageEquipe should disable id FormControl', () => {
        const formGroup = service.createPageEquipeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
