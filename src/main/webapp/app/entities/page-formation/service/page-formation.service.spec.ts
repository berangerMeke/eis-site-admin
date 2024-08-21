import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPageFormation } from '../page-formation.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../page-formation.test-samples';

import { PageFormationService } from './page-formation.service';

const requireRestSample: IPageFormation = {
  ...sampleWithRequiredData,
};

describe('PageFormation Service', () => {
  let service: PageFormationService;
  let httpMock: HttpTestingController;
  let expectedResult: IPageFormation | IPageFormation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PageFormationService);
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

    it('should create a PageFormation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pageFormation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pageFormation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PageFormation', () => {
      const pageFormation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pageFormation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PageFormation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PageFormation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PageFormation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPageFormationToCollectionIfMissing', () => {
      it('should add a PageFormation to an empty array', () => {
        const pageFormation: IPageFormation = sampleWithRequiredData;
        expectedResult = service.addPageFormationToCollectionIfMissing([], pageFormation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageFormation);
      });

      it('should not add a PageFormation to an array that contains it', () => {
        const pageFormation: IPageFormation = sampleWithRequiredData;
        const pageFormationCollection: IPageFormation[] = [
          {
            ...pageFormation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPageFormationToCollectionIfMissing(pageFormationCollection, pageFormation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PageFormation to an array that doesn't contain it", () => {
        const pageFormation: IPageFormation = sampleWithRequiredData;
        const pageFormationCollection: IPageFormation[] = [sampleWithPartialData];
        expectedResult = service.addPageFormationToCollectionIfMissing(pageFormationCollection, pageFormation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageFormation);
      });

      it('should add only unique PageFormation to an array', () => {
        const pageFormationArray: IPageFormation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pageFormationCollection: IPageFormation[] = [sampleWithRequiredData];
        expectedResult = service.addPageFormationToCollectionIfMissing(pageFormationCollection, ...pageFormationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pageFormation: IPageFormation = sampleWithRequiredData;
        const pageFormation2: IPageFormation = sampleWithPartialData;
        expectedResult = service.addPageFormationToCollectionIfMissing([], pageFormation, pageFormation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageFormation);
        expect(expectedResult).toContain(pageFormation2);
      });

      it('should accept null and undefined values', () => {
        const pageFormation: IPageFormation = sampleWithRequiredData;
        expectedResult = service.addPageFormationToCollectionIfMissing([], null, pageFormation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageFormation);
      });

      it('should return initial array if no PageFormation is added', () => {
        const pageFormationCollection: IPageFormation[] = [sampleWithRequiredData];
        expectedResult = service.addPageFormationToCollectionIfMissing(pageFormationCollection, undefined, null);
        expect(expectedResult).toEqual(pageFormationCollection);
      });
    });

    describe('comparePageFormation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePageFormation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePageFormation(entity1, entity2);
        const compareResult2 = service.comparePageFormation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePageFormation(entity1, entity2);
        const compareResult2 = service.comparePageFormation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePageFormation(entity1, entity2);
        const compareResult2 = service.comparePageFormation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
