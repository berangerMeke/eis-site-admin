import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPageService } from '../page-service.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../page-service.test-samples';

import { PageServiceService } from './page-service.service';

const requireRestSample: IPageService = {
  ...sampleWithRequiredData,
};

describe('PageService Service', () => {
  let service: PageServiceService;
  let httpMock: HttpTestingController;
  let expectedResult: IPageService | IPageService[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PageServiceService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PageService', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pageService = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pageService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PageService', () => {
      const pageService = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pageService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PageService', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PageService', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PageService', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPageServiceToCollectionIfMissing', () => {
      it('should add a PageService to an empty array', () => {
        const pageService: IPageService = sampleWithRequiredData;
        expectedResult = service.addPageServiceToCollectionIfMissing([], pageService);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageService);
      });

      it('should not add a PageService to an array that contains it', () => {
        const pageService: IPageService = sampleWithRequiredData;
        const pageServiceCollection: IPageService[] = [
          {
            ...pageService,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPageServiceToCollectionIfMissing(pageServiceCollection, pageService);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PageService to an array that doesn't contain it", () => {
        const pageService: IPageService = sampleWithRequiredData;
        const pageServiceCollection: IPageService[] = [sampleWithPartialData];
        expectedResult = service.addPageServiceToCollectionIfMissing(pageServiceCollection, pageService);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageService);
      });

      it('should add only unique PageService to an array', () => {
        const pageServiceArray: IPageService[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pageServiceCollection: IPageService[] = [sampleWithRequiredData];
        expectedResult = service.addPageServiceToCollectionIfMissing(pageServiceCollection, ...pageServiceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pageService: IPageService = sampleWithRequiredData;
        const pageService2: IPageService = sampleWithPartialData;
        expectedResult = service.addPageServiceToCollectionIfMissing([], pageService, pageService2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageService);
        expect(expectedResult).toContain(pageService2);
      });

      it('should accept null and undefined values', () => {
        const pageService: IPageService = sampleWithRequiredData;
        expectedResult = service.addPageServiceToCollectionIfMissing([], null, pageService, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageService);
      });

      it('should return initial array if no PageService is added', () => {
        const pageServiceCollection: IPageService[] = [sampleWithRequiredData];
        expectedResult = service.addPageServiceToCollectionIfMissing(pageServiceCollection, undefined, null);
        expect(expectedResult).toEqual(pageServiceCollection);
      });
    });

    describe('comparePageService', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePageService(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePageService(entity1, entity2);
        const compareResult2 = service.comparePageService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePageService(entity1, entity2);
        const compareResult2 = service.comparePageService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePageService(entity1, entity2);
        const compareResult2 = service.comparePageService(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
