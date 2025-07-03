import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Step } from '../../models/step';
import { Doing } from '../../models/doing';
import { Router } from '@angular/router';
import { DoingService } from '../../services/doing/doing.service';
import { DoingPayload } from '../../models/DoingPayload';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-doing',
  templateUrl: './add-doing.component.html',
  styleUrl: './add-doing.component.css'
})
export class AddDoingComponent implements OnInit {
  doingForm!: FormGroup;
  step!: Step;
  doing!: DoingPayload;
  parcoursId!: string;

  constructor(private router: Router,
    private doingService: DoingService,
    private fb: FormBuilder) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state) {
      this.step = navigation.extras.state['step'];
    }
    this.doingForm = this.fb.group({
      task: [''],
    });
  }

  ngOnInit(): void {
    this.doing = {
      task: '',
      stepsId: this.step.id
    }


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

  saveDoing() {
    if (this.doingForm.valid) {
      this.doing = this.doingForm.value;
      console.log(this.step);
      this.doing.stepsId = this.step.id;
      console.log(this.doing);
      this.doingService.saveDoing(this.doing).subscribe({

        next: (response) => {

          Swal.fire({
            title: 'Exercice Added',
            text: 'Exercice saved successfully. Do you want to proceed?',
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

              this.router.navigate(['/parcours']);
            }
          });
        },
        error: (err) => {
          console.error('Error saving learning part:', err);
          Swal.fire('Error', 'Failed to save Exercice. Please try again.', 'error');
        }
      });
    }
  }

}
