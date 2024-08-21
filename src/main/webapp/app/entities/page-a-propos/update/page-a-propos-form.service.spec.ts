import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../page-a-propos.test-samples';

import { PageAProposFormService } from './page-a-propos-form.service';

describe('PageAPropos Form Service', () => {
  let service: PageAProposFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PageAProposFormService);
  });

  describe('Service methods', () => {
    describe('createPageAProposFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPageAProposFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sec1Img1: expect.any(Object),
            sec1Desc1: expect.any(Object),
            sec1Logo: expect.any(Object),
            sec1Img2: expect.any(Object),
            sec1Desc2: expect.any(Object),
            sec1Img3: expect.any(Object),
            sec1Desc3: expect.any(Object),
            sec2Titre: expect.any(Object),
            sec2Img: expect.any(Object),
            sec2SousTitre: expect.any(Object),
            sec2Text: expect.any(Object),
            sec3Titre: expect.any(Object),
            sec3Img: expect.any(Object),
            sec3SousTitre: expect.any(Object),
            sec3Text: expect.any(Object),
            sec4Img: expect.any(Object),
            sec4Desc1: expect.any(Object),
            sec4Desc2: expect.any(Object),
            sec4Desc3: expect.any(Object),
            sec4Desc4: expect.any(Object),
            sec5Titre: expect.any(Object),
            membreEquipe1: expect.any(Object),
          })
        );
      });

      it('passing IPageAPropos should create a new form with FormGroup', () => {
        const formGroup = service.createPageAProposFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sec1Img1: expect.any(Object),
            sec1Desc1: expect.any(Object),
            sec1Logo: expect.any(Object),
            sec1Img2: expect.any(Object),
            sec1Desc2: expect.any(Object),
            sec1Img3: expect.any(Object),
            sec1Desc3: expect.any(Object),
            sec2Titre: expect.any(Object),
            sec2Img: expect.any(Object),
            sec2SousTitre: expect.any(Object),
            sec2Text: expect.any(Object),
            sec3Titre: expect.any(Object),
            sec3Img: expect.any(Object),
            sec3SousTitre: expect.any(Object),
            sec3Text: expect.any(Object),
            sec4Img: expect.any(Object),
            sec4Desc1: expect.any(Object),
            sec4Desc2: expect.any(Object),
            sec4Desc3: expect.any(Object),
            sec4Desc4: expect.any(Object),
            sec5Titre: expect.any(Object),
            membreEquipe1: expect.any(Object),
          })
        );
      });
    });

    describe('getPageAPropos', () => {
      it('should return NewPageAPropos for default PageAPropos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPageAProposFormGroup(sampleWithNewData);

        const pageAPropos = service.getPageAPropos(formGroup) as any;

        expect(pageAPropos).toMatchObject(sampleWithNewData);
      });

      it('should return NewPageAPropos for empty PageAPropos initial value', () => {
        const formGroup = service.createPageAProposFormGroup();

        const pageAPropos = service.getPageAPropos(formGroup) as any;

        expect(pageAPropos).toMatchObject({});
      });

      it('should return IPageAPropos', () => {
        const formGroup = service.createPageAProposFormGroup(sampleWithRequiredData);

        const pageAPropos = service.getPageAPropos(formGroup) as any;

        expect(pageAPropos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPageAPropos should not enable id FormControl', () => {
        const formGroup = service.createPageAProposFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPageAPropos should disable id FormControl', () => {
        const formGroup = service.createPageAProposFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
