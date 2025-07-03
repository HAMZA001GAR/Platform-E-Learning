import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-list-of-students-in-parcours',
  templateUrl: './list-of-students-in-parcours.component.html',
  styleUrls: ['./list-of-students-in-parcours.component.css']
})
export class ListOfStudentsInParcoursComponent implements OnInit {

  students: any[] = [];
  formationId: number | null = null; // Initialization as null

  // Define the columns to display
  cols = [
    { field: 'fullName', title: 'Full Name' },
    { field: 'email', title: 'Email' }
  ];

  private baseUrl = 'http://localhost:8090';

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const formationIdParam = this.route.snapshot.paramMap.get('formationId');
    
    if (formationIdParam) {
      this.formationId = +formationIdParam;
      this.loadStudents();
    } else {
      console.error('Formation ID is not available in route parameters.');
    }
  }

  loadStudents(): void {
    if (this.formationId !== null) {
      this.getStudentsByFormationId(this.formationId).subscribe((students) => {
        this.students = students.map(student => ({
          id: student.id,
          firstName: student.firstName,
          lastName: student.lastName,
          email: student.email,
          fullName: `${student.firstName} ${student.lastName}` // Combine first and last name
        }));
      });
    }
  }

  getStudentsByFormationId(formationId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/students/by-formations/${formationId}`);
  }
}
