import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPageAccueil } from '../page-accueil.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../page-accueil.test-samples';

import { PageAccueilService } from './page-accueil.service';

const requireRestSample: IPageAccueil = {
  ...sampleWithRequiredData,
};

describe('PageAccueil Service', () => {
  let service: PageAccueilService;
  let httpMock: HttpTestingController;
  let expectedResult: IPageAccueil | IPageAccueil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PageAccueilService);
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

    it('should create a PageAccueil', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pageAccueil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pageAccueil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PageAccueil', () => {
      const pageAccueil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pageAccueil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PageAccueil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PageAccueil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PageAccueil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPageAccueilToCollectionIfMissing', () => {
      it('should add a PageAccueil to an empty array', () => {
        const pageAccueil: IPageAccueil = sampleWithRequiredData;
        expectedResult = service.addPageAccueilToCollectionIfMissing([], pageAccueil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageAccueil);
      });

      it('should not add a PageAccueil to an array that contains it', () => {
        const pageAccueil: IPageAccueil = sampleWithRequiredData;
        const pageAccueilCollection: IPageAccueil[] = [
          {
            ...pageAccueil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPageAccueilToCollectionIfMissing(pageAccueilCollection, pageAccueil);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PageAccueil to an array that doesn't contain it", () => {
        const pageAccueil: IPageAccueil = sampleWithRequiredData;
        const pageAccueilCollection: IPageAccueil[] = [sampleWithPartialData];
        expectedResult = service.addPageAccueilToCollectionIfMissing(pageAccueilCollection, pageAccueil);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageAccueil);
      });

      it('should add only unique PageAccueil to an array', () => {
        const pageAccueilArray: IPageAccueil[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pageAccueilCollection: IPageAccueil[] = [sampleWithRequiredData];
        expectedResult = service.addPageAccueilToCollectionIfMissing(pageAccueilCollection, ...pageAccueilArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pageAccueil: IPageAccueil = sampleWithRequiredData;
        const pageAccueil2: IPageAccueil = sampleWithPartialData;
        expectedResult = service.addPageAccueilToCollectionIfMissing([], pageAccueil, pageAccueil2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageAccueil);
        expect(expectedResult).toContain(pageAccueil2);
      });

      it('should accept null and undefined values', () => {
        const pageAccueil: IPageAccueil = sampleWithRequiredData;
        expectedResult = service.addPageAccueilToCollectionIfMissing([], null, pageAccueil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageAccueil);
      });

      it('should return initial array if no PageAccueil is added', () => {
        const pageAccueilCollection: IPageAccueil[] = [sampleWithRequiredData];
        expectedResult = service.addPageAccueilToCollectionIfMissing(pageAccueilCollection, undefined, null);
        expect(expectedResult).toEqual(pageAccueilCollection);
      });
    });

    describe('comparePageAccueil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePageAccueil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePageAccueil(entity1, entity2);
        const compareResult2 = service.comparePageAccueil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePageAccueil(entity1, entity2);
        const compareResult2 = service.comparePageAccueil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePageAccueil(entity1, entity2);
        const compareResult2 = service.comparePageAccueil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
