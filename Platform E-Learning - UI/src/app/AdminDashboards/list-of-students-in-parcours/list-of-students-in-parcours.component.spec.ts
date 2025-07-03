import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOfStudentsInParcoursComponent } from './list-of-students-in-parcours.component';

describe('ListOfStudentsInParcoursComponent', () => {
  let component: ListOfStudentsInParcoursComponent;
  let fixture: ComponentFixture<ListOfStudentsInParcoursComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListOfStudentsInParcoursComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListOfStudentsInParcoursComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
