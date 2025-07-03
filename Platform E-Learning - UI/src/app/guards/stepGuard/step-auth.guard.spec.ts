import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { stepAuthGuard } from './step-auth.guard';

describe('stepAuthGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => stepAuthGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
