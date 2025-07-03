import { TestBed } from '@angular/core/testing';

import { DoingService } from './doing.service';

describe('DoingService', () => {
  let service: DoingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
