import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../page-service.test-samples';

import { PageServiceFormService } from './page-service-form.service';

describe('PageService Form Service', () => {
  let service: PageServiceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PageServiceFormService);
  });

  describe('Service methods', () => {
    describe('createPageServiceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPageServiceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titre: expect.any(Object),
            soustitre: expect.any(Object),
          })
        );
      });

      it('passing IPageService should create a new form with FormGroup', () => {
        const formGroup = service.createPageServiceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titre: expect.any(Object),
            soustitre: expect.any(Object),
          })
        );
      });
    });

    describe('getPageService', () => {
      it('should return NewPageService for default PageService initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPageServiceFormGroup(sampleWithNewData);

        const pageService = service.getPageService(formGroup) as any;

        expect(pageService).toMatchObject(sampleWithNewData);
      });

      it('should return NewPageService for empty PageService initial value', () => {
        const formGroup = service.createPageServiceFormGroup();

        const pageService = service.getPageService(formGroup) as any;

        expect(pageService).toMatchObject({});
      });

      it('should return IPageService', () => {
        const formGroup = service.createPageServiceFormGroup(sampleWithRequiredData);

        const pageService = service.getPageService(formGroup) as any;

        expect(pageService).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPageService should not enable id FormControl', () => {
        const formGroup = service.createPageServiceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPageService should disable id FormControl', () => {
        const formGroup = service.createPageServiceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
