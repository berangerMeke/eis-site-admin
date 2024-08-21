import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILienMembreEquipe } from '../lien-membre-equipe.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../lien-membre-equipe.test-samples';

import { LienMembreEquipeService } from './lien-membre-equipe.service';

const requireRestSample: ILienMembreEquipe = {
  ...sampleWithRequiredData,
};

describe('LienMembreEquipe Service', () => {
  let service: LienMembreEquipeService;
  let httpMock: HttpTestingController;
  let expectedResult: ILienMembreEquipe | ILienMembreEquipe[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LienMembreEquipeService);
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

    it('should create a LienMembreEquipe', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const lienMembreEquipe = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(lienMembreEquipe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LienMembreEquipe', () => {
      const lienMembreEquipe = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(lienMembreEquipe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LienMembreEquipe', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LienMembreEquipe', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LienMembreEquipe', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLienMembreEquipeToCollectionIfMissing', () => {
      it('should add a LienMembreEquipe to an empty array', () => {
        const lienMembreEquipe: ILienMembreEquipe = sampleWithRequiredData;
        expectedResult = service.addLienMembreEquipeToCollectionIfMissing([], lienMembreEquipe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lienMembreEquipe);
      });

      it('should not add a LienMembreEquipe to an array that contains it', () => {
        const lienMembreEquipe: ILienMembreEquipe = sampleWithRequiredData;
        const lienMembreEquipeCollection: ILienMembreEquipe[] = [
          {
            ...lienMembreEquipe,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLienMembreEquipeToCollectionIfMissing(lienMembreEquipeCollection, lienMembreEquipe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LienMembreEquipe to an array that doesn't contain it", () => {
        const lienMembreEquipe: ILienMembreEquipe = sampleWithRequiredData;
        const lienMembreEquipeCollection: ILienMembreEquipe[] = [sampleWithPartialData];
        expectedResult = service.addLienMembreEquipeToCollectionIfMissing(lienMembreEquipeCollection, lienMembreEquipe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lienMembreEquipe);
      });

      it('should add only unique LienMembreEquipe to an array', () => {
        const lienMembreEquipeArray: ILienMembreEquipe[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const lienMembreEquipeCollection: ILienMembreEquipe[] = [sampleWithRequiredData];
        expectedResult = service.addLienMembreEquipeToCollectionIfMissing(lienMembreEquipeCollection, ...lienMembreEquipeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const lienMembreEquipe: ILienMembreEquipe = sampleWithRequiredData;
        const lienMembreEquipe2: ILienMembreEquipe = sampleWithPartialData;
        expectedResult = service.addLienMembreEquipeToCollectionIfMissing([], lienMembreEquipe, lienMembreEquipe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lienMembreEquipe);
        expect(expectedResult).toContain(lienMembreEquipe2);
      });

      it('should accept null and undefined values', () => {
        const lienMembreEquipe: ILienMembreEquipe = sampleWithRequiredData;
        expectedResult = service.addLienMembreEquipeToCollectionIfMissing([], null, lienMembreEquipe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lienMembreEquipe);
      });

      it('should return initial array if no LienMembreEquipe is added', () => {
        const lienMembreEquipeCollection: ILienMembreEquipe[] = [sampleWithRequiredData];
        expectedResult = service.addLienMembreEquipeToCollectionIfMissing(lienMembreEquipeCollection, undefined, null);
        expect(expectedResult).toEqual(lienMembreEquipeCollection);
      });
    });

    describe('compareLienMembreEquipe', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLienMembreEquipe(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLienMembreEquipe(entity1, entity2);
        const compareResult2 = service.compareLienMembreEquipe(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLienMembreEquipe(entity1, entity2);
        const compareResult2 = service.compareLienMembreEquipe(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLienMembreEquipe(entity1, entity2);
        const compareResult2 = service.compareLienMembreEquipe(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
