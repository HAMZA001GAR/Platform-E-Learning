import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from '../../services/authentication/auth-service.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private router: Router, private authServiceService: AuthServiceService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  login(): void {
    if (this.loginForm.valid) {
      const email = this.loginForm.get('email')?.value;
      const password = this.loginForm.get('password')?.value;

      this.authServiceService.login(email, password).subscribe(
        response => {
          console.log('Login successful:', response);
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.user.roles)
          localStorage.setItem('idoftheuser', response.user.id)
          localStorage.setItem('forid', response.user.formationsId)
          localStorage.setItem('parcoursid', response.user.parcoursId)
          const role = localStorage.getItem('role');
          const userId = localStorage.getItem('idoftheuser')
          if (role == "EXPERT") {
            this.router.navigate(['/parcours']);
          }
          else if (role == "STUDENT") {
            this.router.navigate([`${userId}/step-progress`]);
          }
          else {
            this.router.navigate(['/list-formations']);
          }
        },
        error => {
          console.log('Login failed:', error);
          console.log('errorMessage:', error.errorMessage);
          if (error.errorMessage === 'Invalid email or password') {
            this.errorMessage = 'Invalid email or password';
          }
          else {
            this.errorMessage = 'Try again';
          }
        }
      );
    }
  }
}


