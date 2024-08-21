import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPageEquipe } from '../page-equipe.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../page-equipe.test-samples';

import { PageEquipeService } from './page-equipe.service';

const requireRestSample: IPageEquipe = {
  ...sampleWithRequiredData,
};

describe('PageEquipe Service', () => {
  let service: PageEquipeService;
  let httpMock: HttpTestingController;
  let expectedResult: IPageEquipe | IPageEquipe[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PageEquipeService);
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

    it('should create a PageEquipe', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pageEquipe = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pageEquipe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PageEquipe', () => {
      const pageEquipe = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pageEquipe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PageEquipe', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PageEquipe', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PageEquipe', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPageEquipeToCollectionIfMissing', () => {
      it('should add a PageEquipe to an empty array', () => {
        const pageEquipe: IPageEquipe = sampleWithRequiredData;
        expectedResult = service.addPageEquipeToCollectionIfMissing([], pageEquipe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageEquipe);
      });

      it('should not add a PageEquipe to an array that contains it', () => {
        const pageEquipe: IPageEquipe = sampleWithRequiredData;
        const pageEquipeCollection: IPageEquipe[] = [
          {
            ...pageEquipe,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPageEquipeToCollectionIfMissing(pageEquipeCollection, pageEquipe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PageEquipe to an array that doesn't contain it", () => {
        const pageEquipe: IPageEquipe = sampleWithRequiredData;
        const pageEquipeCollection: IPageEquipe[] = [sampleWithPartialData];
        expectedResult = service.addPageEquipeToCollectionIfMissing(pageEquipeCollection, pageEquipe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageEquipe);
      });

      it('should add only unique PageEquipe to an array', () => {
        const pageEquipeArray: IPageEquipe[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pageEquipeCollection: IPageEquipe[] = [sampleWithRequiredData];
        expectedResult = service.addPageEquipeToCollectionIfMissing(pageEquipeCollection, ...pageEquipeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pageEquipe: IPageEquipe = sampleWithRequiredData;
        const pageEquipe2: IPageEquipe = sampleWithPartialData;
        expectedResult = service.addPageEquipeToCollectionIfMissing([], pageEquipe, pageEquipe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pageEquipe);
        expect(expectedResult).toContain(pageEquipe2);
      });

      it('should accept null and undefined values', () => {
        const pageEquipe: IPageEquipe = sampleWithRequiredData;
        expectedResult = service.addPageEquipeToCollectionIfMissing([], null, pageEquipe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pageEquipe);
      });

      it('should return initial array if no PageEquipe is added', () => {
        const pageEquipeCollection: IPageEquipe[] = [sampleWithRequiredData];
        expectedResult = service.addPageEquipeToCollectionIfMissing(pageEquipeCollection, undefined, null);
        expect(expectedResult).toEqual(pageEquipeCollection);
      });
    });

    describe('comparePageEquipe', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePageEquipe(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePageEquipe(entity1, entity2);
        const compareResult2 = service.comparePageEquipe(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePageEquipe(entity1, entity2);
        const compareResult2 = service.comparePageEquipe(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePageEquipe(entity1, entity2);
        const compareResult2 = service.comparePageEquipe(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
