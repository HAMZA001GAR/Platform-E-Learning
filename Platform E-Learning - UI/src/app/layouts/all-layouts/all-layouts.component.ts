import { Component } from '@angular/core';
import { SignInComponent } from '../../Auth/sign-in/sign-in.component';

@Component({
  selector: 'app-all-layouts',
  templateUrl: './all-layouts.component.html',
  styleUrl: './all-layouts.component.css'
})
export class AllLayoutsComponent {

  userRole: string | null = null; 

  constructor() { }

  ngOnInit(): void {
    this.userRole = this.getUserRole();
  console.log('User Role:', this.userRole);
  }

  getUserRole(): string | null {
    const role = localStorage.getItem('role');
    return role !== null ? role : null;
  }
}
