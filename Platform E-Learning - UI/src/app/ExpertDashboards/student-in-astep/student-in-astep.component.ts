import { Component, OnInit } from '@angular/core';
import { StudentStepService } from '../../services/StudentStep/student-step.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-student-in-astep',
  templateUrl: './student-in-astep.component.html',
  styleUrls: ['./student-in-astep.component.css'],
  providers: [DatePipe]
})
export class StudentInAStepComponent implements OnInit {

  cols = [
    { field: 'id', title: 'ID' },
    { field: 'fullName', title: 'Full Name' },
    { field: 'taskUrl', title: 'Task URL' },
    { field: 'startDate', title: 'Start Date' },
    { field: 'endDate', title: 'End Date' },
    { field: 'stepStatus', title: 'Step Status' },
    { field: 'actions', title: 'Actions' },
  ];

  
  students: any[] = [];

  constructor(
    private studentStepService: StudentStepService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const stepId = Number(params.get('stepId'));
      if (stepId) {
        this.studentStepService.getStudentsInStep(stepId).subscribe(data => {
          this.students = data.map((studentStep: {
            id: number; firstName: string; lastName: string; taskUrl: string; startDate: string; endDate: string; stepStatus: boolean; studentId: number; stepId: number; parcoursId: number;
          }) => ({
            id: studentStep.id,
            fullName: `${studentStep.firstName} ${studentStep.lastName}`,
            taskUrl: studentStep.taskUrl,
            startDate: this.formatDate(studentStep.startDate),
            endDate: this.formatDate(studentStep.endDate),
            stepStatus: studentStep.stepStatus ? 'Finished' : 'In Progress',
            studentId: studentStep.studentId,
            stepId: studentStep.stepId,
            parcoursId: studentStep.parcoursId,
          }));
        });
      }
    });
  }

  formatDate(date: string | null): string {
    if (!date) {
      return ''; 
    }
    return this.datePipe.transform(date, 'yyyy-MM-ddTHH:mm') || '';
  }
  

  deleteStudentStep(studentId: number, stepId: number, parcoursId: number): void {
    this.studentStepService.deleteStudentStep(studentId, stepId, parcoursId).subscribe(
      () => {

        this.students = this.students.filter(student => student.studentId !== studentId || student.stepId !== stepId || student.parcoursId !== parcoursId);
      },
      error => {

        console.error('Error deleting student step:', error);
      }
    );
  }


  onRowClick(row: any): void {
    console.log("Row clicked:", row);
    this.router.navigate(['/student-progress', row.id]); 
  }
}