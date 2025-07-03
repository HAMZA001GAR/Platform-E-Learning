import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LearningDoingComponent } from './learning-doing.component';

describe('LearningDoingComponent', () => {
  let component: LearningDoingComponent;
  let fixture: ComponentFixture<LearningDoingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LearningDoingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LearningDoingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
