import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { StepsService } from '../../services/steps/steps.service';
import { Step } from '../../models/step';
import { Parcours } from '../../models/parcours';
import { Observable, Subject, of, startWith } from 'rxjs';
import { map } from 'rxjs/operators';
import { ParcoursService } from '../../services/parcours/parcours.service';
import { Router } from '@angular/router';
import { StepPayload } from '../../models/StepPayload';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-add-step',
  templateUrl: './add-step.component.html',
  styleUrls: ['./add-step.component.css']
})
export class AddStepComponent implements OnInit {
  stepForm!: FormGroup;
  parcoursCtrl = new FormControl();
  filteredParcours!: Observable<Parcours[]>;
  allParcours: Parcours[] = [];
  par!: string;
  step!: Step;
  selectedFile!: File;
  showSuccessAlert: boolean = false;
  parcoursId!: string;
  

  constructor(
    private fb: FormBuilder,
    private stepsService: StepsService,
    private parcoursService: ParcoursService,
    private router: Router,
  ) {
    this.stepForm = this.fb.group({
      title: [''],
      description: [''],
      durationInMinutes: [''],
      parcours: [''],
      stepProcess: ['']
    });

  
  }

  ngOnInit(): void {
    this.step = {
      id: 1,
      title: '',
      description: '',
      durationInMinutes: 0,
      parcours: {
        id: 1,
        parcoursName: '',
        parcoursDescription: '',
        imageUrl: ''
      },
      stepProcess: '',
      imageUrl: "",
      startTime: "",
      endTime: "",
      status: false,
      completed: false,
      count: 0,
      progress: 0,
    };
    this.loadParcours();
    this.filteredParcours = this.parcoursCtrl.valueChanges.pipe(
      startWith(''),
      map(value => this._filterParcours(value))
    );

    const storedParcoursId = localStorage.getItem('parcoursid');
  if (storedParcoursId) {
    this.parcoursId = storedParcoursId;
  } else {
    console.error('No parcoursId found in localStorage.');
  }
  }

  NavigateToModules ():void {
    this.router.navigate([`/steps/parcours/${this.parcoursId}`]);
  }

  private _filterParcours(value: string): Parcours[] {
    const filterValue = value.toLowerCase();
    return this.allParcours.filter(parcours => parcours.parcoursName.toLowerCase().includes(filterValue));
  }

  loadParcours() {
    this.parcoursService.getAllParcours().subscribe(
      data => {
        this.allParcours = data;
      },
      error => {
        console.error('Erreur lors du chargement des parcours: ', error);
      }
    );
  }

  onParcoursSelected(parcours: Parcours) {
    this.par = parcours.parcoursName;
    this.step.parcours = parcours;
    this.parcoursCtrl.setValue(parcours.parcoursName, { emitEvent: false });
  }

  selectedFileName: string = '';

  onImageChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      this.selectedFileName = file.name;
    }
  }

  saveStep(): void {
    if (this.stepForm.valid) {
      const formValues = this.stepForm.value;
      const selectedParcours = this.allParcours.find(p => p.parcoursName === this.par);

      const payload: StepPayload = {
        title: formValues.title,
        description: formValues.description,
        durationInMinutes: formValues.durationInMinutes,
        parcoursId: selectedParcours ? selectedParcours.id : null,
        stepProcess: formValues.stepProcess,
        imageUrl: ""
      };

      console.log(payload);
      this.stepsService.saveStep(payload, this.selectedFile).subscribe({
        next: (response) => {

          Swal.fire({
          title: 'Module Added',
          text: 'Module saved successfully. Do you want to proceed?',
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
              this.router.navigate(['/add-learning'], { state: { step: response } });
            }
          });
        },
        error: (err) => {
          console.error('Error saving step:', err);
          Swal.fire('Error', 'Failed to save step. Please try again.', 'error');

        }
      });
    }
  }


}