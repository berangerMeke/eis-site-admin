import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContact } from '../contact.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../contact.test-samples';

import { ContactService } from './contact.service';

const requireRestSample: IContact = {
  ...sampleWithRequiredData,
};

describe('Contact Service', () => {
  let service: ContactService;
  let httpMock: HttpTestingController;
  let expectedResult: IContact | IContact[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContactService);
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

    it('should create a Contact', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const contact = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contact).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Contact', () => {
      const contact = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contact).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Contact', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Contact', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Contact', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContactToCollectionIfMissing', () => {
      it('should add a Contact to an empty array', () => {
        const contact: IContact = sampleWithRequiredData;
        expectedResult = service.addContactToCollectionIfMissing([], contact);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contact);
      });

      it('should not add a Contact to an array that contains it', () => {
        const contact: IContact = sampleWithRequiredData;
        const contactCollection: IContact[] = [
          {
            ...contact,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContactToCollectionIfMissing(contactCollection, contact);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Contact to an array that doesn't contain it", () => {
        const contact: IContact = sampleWithRequiredData;
        const contactCollection: IContact[] = [sampleWithPartialData];
        expectedResult = service.addContactToCollectionIfMissing(contactCollection, contact);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contact);
      });

      it('should add only unique Contact to an array', () => {
        const contactArray: IContact[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contactCollection: IContact[] = [sampleWithRequiredData];
        expectedResult = service.addContactToCollectionIfMissing(contactCollection, ...contactArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contact: IContact = sampleWithRequiredData;
        const contact2: IContact = sampleWithPartialData;
        expectedResult = service.addContactToCollectionIfMissing([], contact, contact2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contact);
        expect(expectedResult).toContain(contact2);
      });

      it('should accept null and undefined values', () => {
        const contact: IContact = sampleWithRequiredData;
        expectedResult = service.addContactToCollectionIfMissing([], null, contact, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contact);
      });

      it('should return initial array if no Contact is added', () => {
        const contactCollection: IContact[] = [sampleWithRequiredData];
        expectedResult = service.addContactToCollectionIfMissing(contactCollection, undefined, null);
        expect(expectedResult).toEqual(contactCollection);
      });
    });

    describe('compareContact', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContact(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContact(entity1, entity2);
        const compareResult2 = service.compareContact(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContact(entity1, entity2);
        const compareResult2 = service.compareContact(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContact(entity1, entity2);
        const compareResult2 = service.compareContact(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
