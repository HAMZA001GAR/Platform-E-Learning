import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormationService } from '../../services/formations/formation.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-manage-formations',
  templateUrl: './manage-formations.component.html',
  styleUrls: ['./manage-formations.component.css']
})
export class ManageFormationsComponent implements OnInit {
  formationForm!: FormGroup;
  selectedFile!: File;
  

  @ViewChild('fileInput', { static: false }) fileInput!: ElementRef;

  constructor(
    private fb: FormBuilder,
    private formationService: FormationService
  ) { }

  ngOnInit(): void {
    this.formationForm = this.fb.group({
      formationsName: ['', Validators.required],
      formationsDescription: ['', Validators.required],
      imageUrl: [''],
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
  if (this.formationForm.valid && this.selectedFile) {
    this.formationService.saveFormation(this.formationForm.value, this.selectedFile).subscribe({
      next: (response) => {
        console.log('Formation saved successfully', response);
        this.formationForm.reset();

        Swal.fire({
          icon: 'success',
          title: 'Success!',
          text: 'Formation saved successfully.',
          timer: 2000,
          showConfirmButton: false,
        });
      },
      error: (error) => {
        console.error('Error saving formation', error);

        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error saving formation. Please try again.',
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
