import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IService } from '../service.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../service.test-samples';

import { ServiceService } from './service.service';

const requireRestSample: IService = {
  ...sampleWithRequiredData,
};

describe('Service Service', () => {
  let service: ServiceService;
  let httpMock: HttpTestingController;
  let expectedResult: IService | IService[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ServiceService);
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

    it('should create a Service', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const service = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(service).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Service', () => {
      const service = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(service).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Service', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Service', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Service', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServiceToCollectionIfMissing', () => {
      it('should add a Service to an empty array', () => {
        const service: IService = sampleWithRequiredData;
        expectedResult = service.addServiceToCollectionIfMissing([], service);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(service);
      });

      it('should not add a Service to an array that contains it', () => {
        const service: IService = sampleWithRequiredData;
        const serviceCollection: IService[] = [
          {
            ...service,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServiceToCollectionIfMissing(serviceCollection, service);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Service to an array that doesn't contain it", () => {
        const service: IService = sampleWithRequiredData;
        const serviceCollection: IService[] = [sampleWithPartialData];
        expectedResult = service.addServiceToCollectionIfMissing(serviceCollection, service);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(service);
      });

      it('should add only unique Service to an array', () => {
        const serviceArray: IService[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const serviceCollection: IService[] = [sampleWithRequiredData];
        expectedResult = service.addServiceToCollectionIfMissing(serviceCollection, ...serviceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const service: IService = sampleWithRequiredData;
        const service2: IService = sampleWithPartialData;
        expectedResult = service.addServiceToCollectionIfMissing([], service, service2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(service);
        expect(expectedResult).toContain(service2);
      });

      it('should accept null and undefined values', () => {
        const service: IService = sampleWithRequiredData;
        expectedResult = service.addServiceToCollectionIfMissing([], null, service, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(service);
      });

      it('should return initial array if no Service is added', () => {
        const serviceCollection: IService[] = [sampleWithRequiredData];
        expectedResult = service.addServiceToCollectionIfMissing(serviceCollection, undefined, null);
        expect(expectedResult).toEqual(serviceCollection);
      });
    });

    describe('compareService', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareService(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareService(entity1, entity2);
        const compareResult2 = service.compareService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareService(entity1, entity2);
        const compareResult2 = service.compareService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareService(entity1, entity2);
        const compareResult2 = service.compareService(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
