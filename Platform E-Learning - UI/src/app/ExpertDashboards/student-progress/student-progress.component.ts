import { animate, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../../services/userService/user-service.service';
import { ActivatedRoute, Params } from '@angular/router';
import { Student } from '../../models/student';
import { ParcoursService } from '../../services/parcours/parcours.service';
import { StudentStepService } from '../../services/StudentStep/student-step.service';
import { StudentStep } from '../../models/StudentStep';

@Component({
  selector: 'app-student-progress',
  templateUrl: './student-progress.component.html',
  styleUrl: './student-progress.component.css',
  animations: [
    trigger('toggleAnimation', [
      transition(':enter', [style({ opacity: 0, transform: 'scale(0.95)' }), animate('100ms ease-out', style({ opacity: 1, transform: 'scale(1)' }))]),
      transition(':leave', [animate('75ms', style({ opacity: 0, transform: 'scale(0.95)' }))]),
    ]),
  ],
})
export class StudentProgressComponent implements OnInit {

  private id!: number;
  studentList: Student | undefined;
  parcourname!: String;
  parcoursId: any = localStorage.getItem('parcoursid');
  studentStepList: StudentStep[] = [];
  constructor(private userServiceService: UserServiceService,
    private route: ActivatedRoute,
    private parcoursService: ParcoursService,
    private studentStepService: StudentStepService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.id = +params['id'];
      this.getStudentById(this.id);
      this.getParcours();
      this.findCompletedStepsByParcoursId(this.id);
    });
  }

  getParcours() {
    if (this.parcoursId !== null) {
      this.parcoursService.getParcoursById(this.parcoursId).subscribe(
        (data) => {
          this.parcourname = data.parcoursName;
  
        }
      )
    }
  }


  getStudentById(id: number) {
    if (this.id) {
      this.userServiceService.getStudentById(this.id).subscribe(
        (data) => {
          this.studentList = data;
        },
        (error) => {
          console.error('Error fetching students', error);
        }
      );
    } else {
      console.error('No student found');
    }
  }

  findCompletedStepsByParcoursId(id: number) {
    if (this.id && this.parcoursId !== null) {
      this.studentStepService.findCompletedStepsByParcoursId(this.parcoursId, this.id).subscribe(
        (data: any[]) => {
          this.studentStepList = this.transformData(data);
          
        },
        (error) => {
          console.error('Error fetching steps', error);
        }
      );
    } else {
      console.error('No step found');
    }
  }

  transformData(data: any[]): StudentStep[] {
    return data.map(item => ({
      title: item[0],
      startTime: new Date(item[1]), 
      endTime: new Date(item[2]),   
      taskUrl: item[3],
      stepId: item[4],
      studentId: item[5]
    }));

}

}
