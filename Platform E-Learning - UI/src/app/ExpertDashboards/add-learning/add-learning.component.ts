import { Component, OnInit } from '@angular/core';
import { Step } from '../../models/step';
import { Router } from '@angular/router';
import { LearningService } from '../../services/learning/learning.service';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { LearningPayload } from '../../models/LearningPayload';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-learning',
  templateUrl: './add-learning.component.html',
  styleUrls: ['./add-learning.component.css']
})

export class AddLearningComponent implements OnInit {

  learningForm!: FormGroup;
  step!: Step;
  learning!: LearningPayload;
  parcoursId!: string;

  constructor(private router: Router,
    private learningService: LearningService,
    private fb: FormBuilder) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state) {
      this.step = navigation.extras.state['step'];
    }
    this.learningForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      urls: this.fb.array([this.fb.group({ url: ['', Validators.required] })])
    });
  }

  ngOnInit(): void {
    this.learning = {
      title: '',
      description: '',
      url: [],
      stepsId: this.step.id
    }
    console.log(this.step);
    
    const storedParcoursId = localStorage.getItem('parcoursid');
    if (storedParcoursId) {
      this.parcoursId = storedParcoursId;
    } else {
      console.error('No parcoursId found in localStorage.');
    }
  }

  NavigateToModules(): void {
    this.router.navigate([`/steps/parcours/${this.parcoursId}`]);
  }

  get urls() {
    return this.learningForm.get('urls') as FormArray;
  }


  addUrl(): void {
    this.urls.push(this.fb.group({ url: ['', Validators.required] }));
  }

  removeUrl(index: number): void {
    this.urls.removeAt(index);
  }

  saveLearning() {
    if (this.learningForm.valid) {
      const formValues = this.learningForm.value;
      this.learning = {
        title: formValues.title,
        description: formValues.description,
        url: formValues.urls.map((group: { url: string }) => group.url),
        stepsId: this.step.id
      };

      console.log(this.learning);
      this.learningService.saveLearning(this.learning).subscribe({
        next: (response) => {

          Swal.fire({
            title: 'Lesson Added',
            text: 'Lesson saved successfully. Do you want to proceed?',
            icon: 'success',
            showCancelButton: true,
            confirmButtonText: 'Yes, proceed',
            cancelButtonText: 'No, stay here',
            customClass: {
              popup: 'modern-swal-popup',
              confirmButton: 'modern-swal-confirm-btn',
              cancelButton: 'modern-swal-cancel-btn'
            }
          }).then((result) => {
            if (result.isConfirmed) {

              this.router.navigate(['/add-doing'], { state: { step: this.step } });
            }
          });
        },
        error: (err) => {
          console.error('Error saving learning part:', err);
          Swal.fire('Error', 'Failed to save Lesson. Please try again.', 'error');
        }
      });
    }
  }
}
