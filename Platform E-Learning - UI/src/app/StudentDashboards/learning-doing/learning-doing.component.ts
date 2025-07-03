import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService, Step, StepWithStatus } from '../services/DataService';
import { catchError, map, Observable, throwError } from 'rxjs';
import { interval, Subscription } from 'rxjs';

interface Learning {
  id: number;
  title: string;
  description: string;
  url: string[];
  stepsId: number;
}

interface Doing {
  id: number;
  task: string;
}

@Component({
  selector: 'app-learning-doing',
  templateUrl: './learning-doing.component.html',
  styleUrls: ['./learning-doing.component.css']
})
export class LearningDoingComponent implements OnInit, OnDestroy {
  private apiLearning = 'http://localhost:8090';
  learnings: Learning[] = [];
  doings: Doing[] = [];
  studentId: number = 0;
  stepsId: number = 0;
  currentTaskUrl: string = '';
  studentSteps: StepWithStatus[] = [];
  remainingTime: string = '';

  showConfirmationModal = false;
  private countdownSubscription!: Subscription;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private dataService: DataService
  ) {}

  ngOnInit(): void {
    // Get studentId from local storage
    const studentIdStr = localStorage.getItem('idoftheuser');
    if (studentIdStr) {
      this.studentId = +studentIdStr;
    } else {
      console.error('Student ID not found in local storage.');
      return;
    }

    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam !== null) {
        this.stepsId = +idParam;
        console.log(`Retrieved stepsId from route params: ${this.stepsId}`);

        // Fetch data using stepsId
        this.getLearning(this.stepsId).subscribe(data => {
          this.learnings = data;
          console.log('Retrieved learnings:', this.learnings);
        }, error => {
          console.error('Error fetching learnings:', error);
        });

        this.getDoing(this.stepsId).subscribe(data => {
          this.doings = data;
          console.log('Retrieved doings:', this.doings);
        }, error => {
          console.error('Error fetching doings:', error);
        });

        // Fetch student steps info and start countdown
        this.getStudentStep(this.studentId, this.stepsId).subscribe(data => {
          this.studentSteps = data;
          console.log('Retrieved student step info:', this.studentSteps);

          // Check if completed and navigate to settings-profile if true
          if (this.studentSteps.length > 0 && this.studentSteps[0].completed) {
            this.router.navigate([`/${this.studentId}/step-progress`]);
            return;
          }

          // Start countdown if endTime is available
          if (this.studentSteps.length > 0 && this.studentSteps[0].endTime) {
            const endTime = new Date(this.studentSteps[0].endTime).getTime();

       
            this.countdownSubscription = interval(1000).subscribe(() => {
              const currentTime = new Date().getTime();
              const difference = endTime - currentTime;
              if (difference > 0) {
                this.remainingTime = this.formatTime(difference);
              } else {
                this.remainingTime = 'Time expired';
                this.countdownSubscription.unsubscribe(); 
              }
            });
          }
        }, error => {
          if (error.status === 404) {
            this.router.navigate([`/${this.studentId}/step-progress`]);
          } else {
            console.error('Error fetching student step info:', error);
          }
        });
      }
    });
  }

  ngOnDestroy(): void {
    if (this.countdownSubscription) {
      this.countdownSubscription.unsubscribe();
    }
  }

  getStudentStep(studentId: number, stepsId: number): Observable<StepWithStatus[]> {
    return this.http.get<StepWithStatus>(`http://localhost:8090/student-steps/stepInfo/${studentId}/${stepsId}`)
      .pipe(
        map(data => [data]), // Wrap the single object in an array
        catchError(error => {
          console.error('Error fetching student step info:', error);
          return throwError(error); // Rethrow the error
        })
      );
  }

  getLearning(stepsId: number): Observable<Learning[]> {
    return this.http.get<Learning[]>(`${this.apiLearning}/learnings?stepsId=${stepsId}`);
  }

  getDoing(stepsId: number): Observable<Doing[]> {
    return this.http.get<Doing[]>(`${this.apiLearning}/doings?stepsId=${stepsId}`);
  }

  openConfirmationModal(taskUrl: string): void {
    this.currentTaskUrl = taskUrl;
    this.showConfirmationModal = true;
  }

  completeStep(): void {
    if (!this.stepsId) {
      console.error('Steps ID is not set.');
      return;
    }

    this.dataService.completeStep(this.studentId, this.stepsId, this.currentTaskUrl).subscribe(() => {
      console.log('Completed step with stepsId:', this.stepsId);
      this.router.navigate([this.studentId, 'step-progress']);
    }, error => {
      console.error('Error completing step:', error);
    });

    this.showConfirmationModal = false; // Close the modal after action
  }

  cancelStep(): void {
    this.showConfirmationModal = false; // Close the modal without action
  }

  formatTime(ms: number): string {
    const seconds = Math.floor((ms / 1000) % 60);
    const minutes = Math.floor((ms / (1000 * 60)) % 60);
    const hours = Math.floor((ms / (1000 * 60 * 60)) % 24);
    const days = Math.floor(ms / (1000 * 60 * 60 * 24));

    if (days === 0 && hours === 0) {
      return `${minutes} minutes : ${seconds} seconds`;
    } else if (days === 0) {
      return `${hours} hours :${minutes} minutes :${seconds} seconds`;
    } else {
      return `${days} days :${hours} hours :${minutes} minutes :${seconds} seconds`;
    }
  }
}







