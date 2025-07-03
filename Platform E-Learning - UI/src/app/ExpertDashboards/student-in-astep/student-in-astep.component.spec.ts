import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentInAStepComponent } from './student-in-astep.component';

describe('StudentInAStepComponent', () => {
  let component: StudentInAStepComponent;
  let fixture: ComponentFixture<StudentInAStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentInAStepComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentInAStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
