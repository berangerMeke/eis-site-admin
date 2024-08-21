import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../page-accueil.test-samples';

import { PageAccueilFormService } from './page-accueil-form.service';

describe('PageAccueil Form Service', () => {
  let service: PageAccueilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PageAccueilFormService);
  });

  describe('Service methods', () => {
    describe('createPageAccueilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPageAccueilFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sec1Titre: expect.any(Object),
            sec1Texte: expect.any(Object),
            secImage: expect.any(Object),
            sec1Bouton: expect.any(Object),
            sec2Titre: expect.any(Object),
            sec2Text: expect.any(Object),
            sec2Bouton: expect.any(Object),
            sec3Titre: expect.any(Object),
            sec3Blog1Img: expect.any(Object),
            sec3Blog2Img: expect.any(Object),
            sec3Blog3Img: expect.any(Object),
            sec3Blog1SousTitre: expect.any(Object),
            sec3Blog2SousTitre: expect.any(Object),
            sec3Blog3SousTitre: expect.any(Object),
            sec3Blog1Text: expect.any(Object),
            sec3Blog2Text: expect.any(Object),
            sec3Blog3Text: expect.any(Object),
            sec4Titre: expect.any(Object),
            sec4Text: expect.any(Object),
            sec4Blog1Img: expect.any(Object),
            sec4Blog1SousTitre: expect.any(Object),
            sec4Blog2SousTitre: expect.any(Object),
            sec4Blog1Text: expect.any(Object),
            sec4Blog2Text: expect.any(Object),
            sec5Titre: expect.any(Object),
            sec5Blog1Img: expect.any(Object),
            sec5Blog2Img: expect.any(Object),
            sec5Blog3Img: expect.any(Object),
            sec5Blog4Img: expect.any(Object),
            sec5Blog5Img: expect.any(Object),
            sec5Blog6Img: expect.any(Object),
            sec5Blog7Img: expect.any(Object),
            sec5Blog1SousTitre: expect.any(Object),
            sec5Blog2SousTitre: expect.any(Object),
            sec5Blog3SousTitre: expect.any(Object),
            sec5Blog4SousTitre: expect.any(Object),
            sec5Blog5SousTitre: expect.any(Object),
            sec5Blog6SousTitre: expect.any(Object),
            sec5Blog7SousTitre: expect.any(Object),
            sec5Blog1Text: expect.any(Object),
            sec5Blog2Text: expect.any(Object),
            sec5Blog3Text: expect.any(Object),
            sec5Blog4Text: expect.any(Object),
            sec5Blog5Text: expect.any(Object),
            sec5Blog6Text: expect.any(Object),
            sec5Blog7Text: expect.any(Object),
            sec6Titre: expect.any(Object),
            sec6Img: expect.any(Object),
            sec6Text: expect.any(Object),
            sec7Titre: expect.any(Object),
            sec7Img: expect.any(Object),
            sec7Text: expect.any(Object),
            sec8Titre: expect.any(Object),
            sec8Img: expect.any(Object),
            sec8Text: expect.any(Object),
            sec9Titre: expect.any(Object),
            sec9Img: expect.any(Object),
            sec9Text: expect.any(Object),
            partenaires1: expect.any(Object),
          })
        );
      });

      it('passing IPageAccueil should create a new form with FormGroup', () => {
        const formGroup = service.createPageAccueilFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sec1Titre: expect.any(Object),
            sec1Texte: expect.any(Object),
            secImage: expect.any(Object),
            sec1Bouton: expect.any(Object),
            sec2Titre: expect.any(Object),
            sec2Text: expect.any(Object),
            sec2Bouton: expect.any(Object),
            sec3Titre: expect.any(Object),
            sec3Blog1Img: expect.any(Object),
            sec3Blog2Img: expect.any(Object),
            sec3Blog3Img: expect.any(Object),
            sec3Blog1SousTitre: expect.any(Object),
            sec3Blog2SousTitre: expect.any(Object),
            sec3Blog3SousTitre: expect.any(Object),
            sec3Blog1Text: expect.any(Object),
            sec3Blog2Text: expect.any(Object),
            sec3Blog3Text: expect.any(Object),
            sec4Titre: expect.any(Object),
            sec4Text: expect.any(Object),
            sec4Blog1Img: expect.any(Object),
            sec4Blog1SousTitre: expect.any(Object),
            sec4Blog2SousTitre: expect.any(Object),
            sec4Blog1Text: expect.any(Object),
            sec4Blog2Text: expect.any(Object),
            sec5Titre: expect.any(Object),
            sec5Blog1Img: expect.any(Object),
            sec5Blog2Img: expect.any(Object),
            sec5Blog3Img: expect.any(Object),
            sec5Blog4Img: expect.any(Object),
            sec5Blog5Img: expect.any(Object),
            sec5Blog6Img: expect.any(Object),
            sec5Blog7Img: expect.any(Object),
            sec5Blog1SousTitre: expect.any(Object),
            sec5Blog2SousTitre: expect.any(Object),
            sec5Blog3SousTitre: expect.any(Object),
            sec5Blog4SousTitre: expect.any(Object),
            sec5Blog5SousTitre: expect.any(Object),
            sec5Blog6SousTitre: expect.any(Object),
            sec5Blog7SousTitre: expect.any(Object),
            sec5Blog1Text: expect.any(Object),
            sec5Blog2Text: expect.any(Object),
            sec5Blog3Text: expect.any(Object),
            sec5Blog4Text: expect.any(Object),
            sec5Blog5Text: expect.any(Object),
            sec5Blog6Text: expect.any(Object),
            sec5Blog7Text: expect.any(Object),
            sec6Titre: expect.any(Object),
            sec6Img: expect.any(Object),
            sec6Text: expect.any(Object),
            sec7Titre: expect.any(Object),
            sec7Img: expect.any(Object),
            sec7Text: expect.any(Object),
            sec8Titre: expect.any(Object),
            sec8Img: expect.any(Object),
            sec8Text: expect.any(Object),
            sec9Titre: expect.any(Object),
            sec9Img: expect.any(Object),
            sec9Text: expect.any(Object),
            partenaires1: expect.any(Object),
          })
        );
      });
    });

    describe('getPageAccueil', () => {
      it('should return NewPageAccueil for default PageAccueil initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPageAccueilFormGroup(sampleWithNewData);

        const pageAccueil = service.getPageAccueil(formGroup) as any;

        expect(pageAccueil).toMatchObject(sampleWithNewData);
      });

      it('should return NewPageAccueil for empty PageAccueil initial value', () => {
        const formGroup = service.createPageAccueilFormGroup();

        const pageAccueil = service.getPageAccueil(formGroup) as any;

        expect(pageAccueil).toMatchObject({});
      });

      it('should return IPageAccueil', () => {
        const formGroup = service.createPageAccueilFormGroup(sampleWithRequiredData);

        const pageAccueil = service.getPageAccueil(formGroup) as any;

        expect(pageAccueil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPageAccueil should not enable id FormControl', () => {
        const formGroup = service.createPageAccueilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPageAccueil should disable id FormControl', () => {
        const formGroup = service.createPageAccueilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
