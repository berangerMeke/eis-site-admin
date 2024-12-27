import { TestBed } from '@angular/core/testing';

import { TransfertDataService } from './transfert-data.service';

describe('TransfertDataService', () => {
  let service: TransfertDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransfertDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
