import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFormation } from '../formation.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../formation.test-samples';

import { FormationService } from './formation.service';

const requireRestSample: IFormation = {
  ...sampleWithRequiredData,
};

describe('Formation Service', () => {
  let service: FormationService;
  let httpMock: HttpTestingController;
  let expectedResult: IFormation | IFormation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FormationService);
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

    it('should create a Formation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const formation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(formation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Formation', () => {
      const formation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(formation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Formation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Formation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Formation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFormationToCollectionIfMissing', () => {
      it('should add a Formation to an empty array', () => {
        const formation: IFormation = sampleWithRequiredData;
        expectedResult = service.addFormationToCollectionIfMissing([], formation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formation);
      });

      it('should not add a Formation to an array that contains it', () => {
        const formation: IFormation = sampleWithRequiredData;
        const formationCollection: IFormation[] = [
          {
            ...formation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFormationToCollectionIfMissing(formationCollection, formation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Formation to an array that doesn't contain it", () => {
        const formation: IFormation = sampleWithRequiredData;
        const formationCollection: IFormation[] = [sampleWithPartialData];
        expectedResult = service.addFormationToCollectionIfMissing(formationCollection, formation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formation);
      });

      it('should add only unique Formation to an array', () => {
        const formationArray: IFormation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const formationCollection: IFormation[] = [sampleWithRequiredData];
        expectedResult = service.addFormationToCollectionIfMissing(formationCollection, ...formationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const formation: IFormation = sampleWithRequiredData;
        const formation2: IFormation = sampleWithPartialData;
        expectedResult = service.addFormationToCollectionIfMissing([], formation, formation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formation);
        expect(expectedResult).toContain(formation2);
      });

      it('should accept null and undefined values', () => {
        const formation: IFormation = sampleWithRequiredData;
        expectedResult = service.addFormationToCollectionIfMissing([], null, formation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formation);
      });

      it('should return initial array if no Formation is added', () => {
        const formationCollection: IFormation[] = [sampleWithRequiredData];
        expectedResult = service.addFormationToCollectionIfMissing(formationCollection, undefined, null);
        expect(expectedResult).toEqual(formationCollection);
      });
    });

    describe('compareFormation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFormation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFormation(entity1, entity2);
        const compareResult2 = service.compareFormation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFormation(entity1, entity2);
        const compareResult2 = service.compareFormation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFormation(entity1, entity2);
        const compareResult2 = service.compareFormation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
