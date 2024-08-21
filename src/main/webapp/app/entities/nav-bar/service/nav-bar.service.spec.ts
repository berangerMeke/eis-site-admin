import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INavBar } from '../nav-bar.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nav-bar.test-samples';

import { NavBarService } from './nav-bar.service';

const requireRestSample: INavBar = {
  ...sampleWithRequiredData,
};

describe('NavBar Service', () => {
  let service: NavBarService;
  let httpMock: HttpTestingController;
  let expectedResult: INavBar | INavBar[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NavBarService);
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

    it('should create a NavBar', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const navBar = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(navBar).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NavBar', () => {
      const navBar = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(navBar).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NavBar', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NavBar', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NavBar', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNavBarToCollectionIfMissing', () => {
      it('should add a NavBar to an empty array', () => {
        const navBar: INavBar = sampleWithRequiredData;
        expectedResult = service.addNavBarToCollectionIfMissing([], navBar);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(navBar);
      });

      it('should not add a NavBar to an array that contains it', () => {
        const navBar: INavBar = sampleWithRequiredData;
        const navBarCollection: INavBar[] = [
          {
            ...navBar,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNavBarToCollectionIfMissing(navBarCollection, navBar);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NavBar to an array that doesn't contain it", () => {
        const navBar: INavBar = sampleWithRequiredData;
        const navBarCollection: INavBar[] = [sampleWithPartialData];
        expectedResult = service.addNavBarToCollectionIfMissing(navBarCollection, navBar);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(navBar);
      });

      it('should add only unique NavBar to an array', () => {
        const navBarArray: INavBar[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const navBarCollection: INavBar[] = [sampleWithRequiredData];
        expectedResult = service.addNavBarToCollectionIfMissing(navBarCollection, ...navBarArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const navBar: INavBar = sampleWithRequiredData;
        const navBar2: INavBar = sampleWithPartialData;
        expectedResult = service.addNavBarToCollectionIfMissing([], navBar, navBar2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(navBar);
        expect(expectedResult).toContain(navBar2);
      });

      it('should accept null and undefined values', () => {
        const navBar: INavBar = sampleWithRequiredData;
        expectedResult = service.addNavBarToCollectionIfMissing([], null, navBar, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(navBar);
      });

      it('should return initial array if no NavBar is added', () => {
        const navBarCollection: INavBar[] = [sampleWithRequiredData];
        expectedResult = service.addNavBarToCollectionIfMissing(navBarCollection, undefined, null);
        expect(expectedResult).toEqual(navBarCollection);
      });
    });

    describe('compareNavBar', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNavBar(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNavBar(entity1, entity2);
        const compareResult2 = service.compareNavBar(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNavBar(entity1, entity2);
        const compareResult2 = service.compareNavBar(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNavBar(entity1, entity2);
        const compareResult2 = service.compareNavBar(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
