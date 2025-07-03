import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParcoursService } from '../../services/parcours/parcours.service';
import { FormationService } from '../../services/formations/formation.service';
import { Formation } from '../../models/formation';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-manage-parcours',
  templateUrl: './manage-parcours.component.html',
  styleUrls: ['./manage-parcours.component.css']
})
export class ManageParcoursComponent implements OnInit {
  parcoursForm!: FormGroup;
  selectedFile!: File;
  formations: Formation[] = [];

  @ViewChild('fileInput') fileInput!: ElementRef;

  constructor(
    private fb: FormBuilder,
    private parcoursService: ParcoursService,
    private formationService: FormationService
  ) { }

  ngOnInit(): void {
    this.parcoursForm = this.fb.group({
      parcoursName: ['', Validators.required],
      parcoursDescription: ['', Validators.required],
      formationId: ['', Validators.required],
      imageUrl: ['']
    });

    this.loadFormations();
  }

  loadFormations(): void {
    this.formationService.getFormations().subscribe(formations => {
      this.formations = formations;
    });
  }

  triggerFileInput(): void {
    this.fileInput.nativeElement.click();
  }
  selectedFileName: string = '';
  onImageChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      this.selectedFileName = file.name; 
    }
  }

onSubmit(): void {
  if (this.parcoursForm.valid && this.selectedFile) {
    this.parcoursService.saveParcours(this.parcoursForm.value, this.selectedFile).subscribe({
      next: (response) => {
        console.log('Parcours saved successfully', response);
        this.parcoursForm.reset();

        Swal.fire({
          icon: 'success',
          title: 'Success!',
          text: 'Parcours saved successfully.',
          timer: 2000,
          showConfirmButton: false,
        });
      },
      error: (error) => {
        console.error('Error saving Parcours', error);

        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error saving Parcours. Please try again.',
        });
      }
    });
  } else {
    console.error('Form is not valid or no file selected');

    Swal.fire({
      icon: 'warning',
      title: 'Warning',
      text: 'Please fill the form correctly and select a file.',
    });
  }
}
}

