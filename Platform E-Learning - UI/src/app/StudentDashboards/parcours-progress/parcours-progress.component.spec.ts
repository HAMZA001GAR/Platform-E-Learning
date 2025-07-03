import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParcoursProgressComponent } from './parcours-progress.component';

describe('ParcoursProgressComponent', () => {
  let component: ParcoursProgressComponent;
  let fixture: ComponentFixture<ParcoursProgressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParcoursProgressComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ParcoursProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
