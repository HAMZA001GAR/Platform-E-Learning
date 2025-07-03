import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthServiceService } from '../../services/authentication/auth-service.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: string = '';
  showParcoursDropdown: boolean = false;
  parcoursList: any[] = []; // Assuming you fetch this from a service
  showFormationsDRopDown: boolean = false ; 
  formationsList!: any;

  constructor(
    private fb: FormBuilder,
    private authService: AuthServiceService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      roles: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      parcoursId: [''] ,// Add this control but it will be conditionally required
      formationsId: ['']
    }, { validator: this.passwordMatchValidator });
  }

  ngOnInit(): void {
    // Fetch the list of parcours from the backend if necessary
    this.loadParcours();
    this.loadFormations();
  }

  loadParcours(): void {
    // Assuming you have a service method to fetch the list of parcours
    this.authService.getParcoursList().subscribe(
      data => {
        this.parcoursList = data;
        console.log(this.parcoursList)
      },
      error => {
        console.error('Error loading parcours list', error);
      }
    );
  }

  loadFormations(): void {
    this.authService.getFormationsList().subscribe( 
      formations => {
         this.formationsList = formations;
      },
      error => {
        console.error('Error loading parcours list', error);
      }
    );
  }

  passwordMatchValidator(form: FormGroup) {
    return form.controls['password'].value === form.controls['confirmPassword'].value
      ? null : { 'mismatch': true };
  }

  onRoleChange(event: any): void {
    const selectedRole = event.target.value;
    // Expert
    this.showParcoursDropdown = selectedRole === 'EXPERT' ;
    if (this.showParcoursDropdown) {
      this.registerForm.get('parcoursId')?.setValidators([Validators.required]);
    } else {
      this.registerForm.get('parcoursId')?.clearValidators();
    }
    this.registerForm.get('parcoursId')?.updateValueAndValidity();
    //  Student
    this.showFormationsDRopDown = selectedRole === 'STUDENT';
    if(this.showFormationsDRopDown) {
      this.registerForm.get('formationsId')?.setValidators([Validators.required]);
    }else {
      this.registerForm.get('formationsId')?.clearValidators();
    }
    this.registerForm.get('formationsId')?.updateValueAndValidity();
  }

  register(): void {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe(
        response => {
          console.log('Registration successful', response);
          this.router.navigate(['/signin']);
        },
        error => {
          console.error('Registration error', error);
          this.errorMessage = 'Registration failed. Please try again.';
        }
      );
    }
  }
}
