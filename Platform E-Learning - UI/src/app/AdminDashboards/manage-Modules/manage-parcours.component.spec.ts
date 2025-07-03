import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageParcoursComponent } from './manage-parcours.component';

describe('ManageParcoursComponent', () => {
  let component: ManageParcoursComponent;
  let fixture: ComponentFixture<ManageParcoursComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManageParcoursComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManageParcoursComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
