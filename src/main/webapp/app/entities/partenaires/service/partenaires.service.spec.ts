import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPartenaires } from '../partenaires.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../partenaires.test-samples';

import { PartenairesService } from './partenaires.service';

const requireRestSample: IPartenaires = {
  ...sampleWithRequiredData,
};

describe('Partenaires Service', () => {
  let service: PartenairesService;
  let httpMock: HttpTestingController;
  let expectedResult: IPartenaires | IPartenaires[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartenairesService);
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

    it('should create a Partenaires', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const partenaires = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(partenaires).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Partenaires', () => {
      const partenaires = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(partenaires).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Partenaires', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Partenaires', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Partenaires', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPartenairesToCollectionIfMissing', () => {
      it('should add a Partenaires to an empty array', () => {
        const partenaires: IPartenaires = sampleWithRequiredData;
        expectedResult = service.addPartenairesToCollectionIfMissing([], partenaires);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partenaires);
      });

      it('should not add a Partenaires to an array that contains it', () => {
        const partenaires: IPartenaires = sampleWithRequiredData;
        const partenairesCollection: IPartenaires[] = [
          {
            ...partenaires,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPartenairesToCollectionIfMissing(partenairesCollection, partenaires);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Partenaires to an array that doesn't contain it", () => {
        const partenaires: IPartenaires = sampleWithRequiredData;
        const partenairesCollection: IPartenaires[] = [sampleWithPartialData];
        expectedResult = service.addPartenairesToCollectionIfMissing(partenairesCollection, partenaires);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partenaires);
      });

      it('should add only unique Partenaires to an array', () => {
        const partenairesArray: IPartenaires[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const partenairesCollection: IPartenaires[] = [sampleWithRequiredData];
        expectedResult = service.addPartenairesToCollectionIfMissing(partenairesCollection, ...partenairesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partenaires: IPartenaires = sampleWithRequiredData;
        const partenaires2: IPartenaires = sampleWithPartialData;
        expectedResult = service.addPartenairesToCollectionIfMissing([], partenaires, partenaires2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partenaires);
        expect(expectedResult).toContain(partenaires2);
      });

      it('should accept null and undefined values', () => {
        const partenaires: IPartenaires = sampleWithRequiredData;
        expectedResult = service.addPartenairesToCollectionIfMissing([], null, partenaires, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partenaires);
      });

      it('should return initial array if no Partenaires is added', () => {
        const partenairesCollection: IPartenaires[] = [sampleWithRequiredData];
        expectedResult = service.addPartenairesToCollectionIfMissing(partenairesCollection, undefined, null);
        expect(expectedResult).toEqual(partenairesCollection);
      });
    });

    describe('comparePartenaires', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePartenaires(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePartenaires(entity1, entity2);
        const compareResult2 = service.comparePartenaires(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePartenaires(entity1, entity2);
        const compareResult2 = service.comparePartenaires(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePartenaires(entity1, entity2);
        const compareResult2 = service.comparePartenaires(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
