import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllLayoutsComponent } from './all-layouts.component';

describe('AllLayoutsComponent', () => {
  let component: AllLayoutsComponent;
  let fixture: ComponentFixture<AllLayoutsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllLayoutsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllLayoutsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
