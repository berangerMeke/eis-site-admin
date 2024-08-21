import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMembreEquipe } from '../membre-equipe.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../membre-equipe.test-samples';

import { MembreEquipeService } from './membre-equipe.service';

const requireRestSample: IMembreEquipe = {
  ...sampleWithRequiredData,
};

describe('MembreEquipe Service', () => {
  let service: MembreEquipeService;
  let httpMock: HttpTestingController;
  let expectedResult: IMembreEquipe | IMembreEquipe[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MembreEquipeService);
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

    it('should create a MembreEquipe', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const membreEquipe = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(membreEquipe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MembreEquipe', () => {
      const membreEquipe = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(membreEquipe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MembreEquipe', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MembreEquipe', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MembreEquipe', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMembreEquipeToCollectionIfMissing', () => {
      it('should add a MembreEquipe to an empty array', () => {
        const membreEquipe: IMembreEquipe = sampleWithRequiredData;
        expectedResult = service.addMembreEquipeToCollectionIfMissing([], membreEquipe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(membreEquipe);
      });

      it('should not add a MembreEquipe to an array that contains it', () => {
        const membreEquipe: IMembreEquipe = sampleWithRequiredData;
        const membreEquipeCollection: IMembreEquipe[] = [
          {
            ...membreEquipe,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMembreEquipeToCollectionIfMissing(membreEquipeCollection, membreEquipe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MembreEquipe to an array that doesn't contain it", () => {
        const membreEquipe: IMembreEquipe = sampleWithRequiredData;
        const membreEquipeCollection: IMembreEquipe[] = [sampleWithPartialData];
        expectedResult = service.addMembreEquipeToCollectionIfMissing(membreEquipeCollection, membreEquipe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(membreEquipe);
      });

      it('should add only unique MembreEquipe to an array', () => {
        const membreEquipeArray: IMembreEquipe[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const membreEquipeCollection: IMembreEquipe[] = [sampleWithRequiredData];
        expectedResult = service.addMembreEquipeToCollectionIfMissing(membreEquipeCollection, ...membreEquipeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const membreEquipe: IMembreEquipe = sampleWithRequiredData;
        const membreEquipe2: IMembreEquipe = sampleWithPartialData;
        expectedResult = service.addMembreEquipeToCollectionIfMissing([], membreEquipe, membreEquipe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(membreEquipe);
        expect(expectedResult).toContain(membreEquipe2);
      });

      it('should accept null and undefined values', () => {
        const membreEquipe: IMembreEquipe = sampleWithRequiredData;
        expectedResult = service.addMembreEquipeToCollectionIfMissing([], null, membreEquipe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(membreEquipe);
      });

      it('should return initial array if no MembreEquipe is added', () => {
        const membreEquipeCollection: IMembreEquipe[] = [sampleWithRequiredData];
        expectedResult = service.addMembreEquipeToCollectionIfMissing(membreEquipeCollection, undefined, null);
        expect(expectedResult).toEqual(membreEquipeCollection);
      });
    });

    describe('compareMembreEquipe', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMembreEquipe(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMembreEquipe(entity1, entity2);
        const compareResult2 = service.compareMembreEquipe(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMembreEquipe(entity1, entity2);
        const compareResult2 = service.compareMembreEquipe(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMembreEquipe(entity1, entity2);
        const compareResult2 = service.compareMembreEquipe(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
