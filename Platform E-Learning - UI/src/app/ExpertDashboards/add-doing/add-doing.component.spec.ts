import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDoingComponent } from './add-doing.component';

describe('AddDoingComponent', () => {
  let component: AddDoingComponent;
  let fixture: ComponentFixture<AddDoingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddDoingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddDoingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
