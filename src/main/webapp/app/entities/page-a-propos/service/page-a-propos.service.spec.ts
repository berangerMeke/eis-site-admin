import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPageAPropos } from '../page-a-propos.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../page-a-propos.test-samples';

import { PageAProposService } from './page-a-propos.service';

const requireRestSample: IPageAPropos = {
  ...sampleWithRequiredData,
};

describe('PageAPropos Service', () => {
  let service: PageAProposService;
  let httpMock: HttpTestingController;
  let expectedResult: IPageAPropos | IPageAPropos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PageAProposService);
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

    it('should create a PageAPropos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pageAPropos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pageAPropos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PageAPropos', () => {
      const pageAPropos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pageAPropos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PageAPropos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PageAPropos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PageAPropos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPageAProposToCollectionIfMissing', () => {
      it('should add a PageAPropos to an empty array', () => {
        const pageAPropos: IPageAPropos = sampleWithRequiredData;
        expectedResult = service.addPageAProposToCollectionIfMissing([], pageAPropos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageAPropos);
      });

      it('should not add a PageAPropos to an array that contains it', () => {
        const pageAPropos: IPageAPropos = sampleWithRequiredData;
        const pageAProposCollection: IPageAPropos[] = [
          {
            ...pageAPropos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPageAProposToCollectionIfMissing(pageAProposCollection, pageAPropos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PageAPropos to an array that doesn't contain it", () => {
        const pageAPropos: IPageAPropos = sampleWithRequiredData;
        const pageAProposCollection: IPageAPropos[] = [sampleWithPartialData];
        expectedResult = service.addPageAProposToCollectionIfMissing(pageAProposCollection, pageAPropos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageAPropos);
      });

      it('should add only unique PageAPropos to an array', () => {
        const pageAProposArray: IPageAPropos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pageAProposCollection: IPageAPropos[] = [sampleWithRequiredData];
        expectedResult = service.addPageAProposToCollectionIfMissing(pageAProposCollection, ...pageAProposArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pageAPropos: IPageAPropos = sampleWithRequiredData;
        const pageAPropos2: IPageAPropos = sampleWithPartialData;
        expectedResult = service.addPageAProposToCollectionIfMissing([], pageAPropos, pageAPropos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageAPropos);
        expect(expectedResult).toContain(pageAPropos2);
      });

      it('should accept null and undefined values', () => {
        const pageAPropos: IPageAPropos = sampleWithRequiredData;
        expectedResult = service.addPageAProposToCollectionIfMissing([], null, pageAPropos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageAPropos);
      });

      it('should return initial array if no PageAPropos is added', () => {
        const pageAProposCollection: IPageAPropos[] = [sampleWithRequiredData];
        expectedResult = service.addPageAProposToCollectionIfMissing(pageAProposCollection, undefined, null);
        expect(expectedResult).toEqual(pageAProposCollection);
      });
    });

    describe('comparePageAPropos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePageAPropos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePageAPropos(entity1, entity2);
        const compareResult2 = service.comparePageAPropos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePageAPropos(entity1, entity2);
        const compareResult2 = service.comparePageAPropos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePageAPropos(entity1, entity2);
        const compareResult2 = service.comparePageAPropos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
