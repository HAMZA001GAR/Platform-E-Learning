import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../../services/userService/user-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrl: './analytics.component.css'
})
export class AnalyticsComponent implements OnInit {


  users: any[] = [];
  cols = [
    { field: 'id', title: 'ID' },
    { field: 'fullName', title: 'Full Name' },
    { field: 'Progress', title: 'Progress' },
  ];

  constructor(private userService: UserServiceService, private router: Router) { } // Inject the Router

  parcoursId: number | null = null;


  ngOnInit(): void {
    const parcoursIdString = localStorage.getItem('parcoursid');
    if (parcoursIdString !== null) {
      this.parcoursId = +parcoursIdString;
      this.loadUsersByParcoursId(this.parcoursId);
    } else {
      console.error('parcoursId not found in local storage');
    }


  }

  loadUsersByParcoursId(parcoursId: number): void {
    this.userService.getUsersByParcoursId(parcoursId).subscribe(
      (data) => {
        this.users = data;
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }

  onRowClick(row: any): void {
    this.router.navigate(['/student-progress', row.id]); 
  }


}