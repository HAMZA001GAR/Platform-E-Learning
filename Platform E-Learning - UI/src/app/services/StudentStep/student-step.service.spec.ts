import { TestBed } from '@angular/core/testing';

import { StudentStepService } from './student-step.service';

describe('StudentStepService', () => {
  let service: StudentStepService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentStepService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
