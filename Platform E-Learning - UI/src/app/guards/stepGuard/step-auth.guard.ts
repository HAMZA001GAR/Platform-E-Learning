import { CanActivateFn } from '@angular/router';

export const StepAuthGuard: CanActivateFn = (route, state) => {
  return true;
};
