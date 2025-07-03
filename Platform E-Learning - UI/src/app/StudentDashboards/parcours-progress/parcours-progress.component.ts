import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService, StepWithStatus } from '../services/DataService';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';

export interface Step extends StepWithStatus {
  category: string;
  startTime: string | null;
  endTime: string | null;

}

interface Category {
  icon: string;
  name: string;
  parcoursCount: number;
  date: string;
}

interface Student {
  id: number;
  fullName: string;
}

@Component({
  selector: 'app-parcours-progress',
  templateUrl: './parcours-progress.component.html',
  styleUrls: ['./parcours-progress.component.css']
})
export class ParcoursProgressComponent implements OnInit {

  showConfirmationModal = false;
  currentStep: Step | null = null;

  steps: Step[] = [];
  selectedParcours: number = 1;
  studentId: number = 1;
  parcoursList: any[] = []; // Add this line to store fetched parcours

    studentFormControl = new FormControl();
    filteredStudents: Observable<Student[]> | undefined;

  public backendUrl = "http://localhost:8090";
  
    constructor(
      private route: ActivatedRoute,
      private dataService: DataService,
      private router: Router
    ) {
    }

    ngOnInit(): void {
      this.route.paramMap.subscribe(params => {
        this.studentId = +params.get('userId')!;
        this.loadSteps();
        this.loadParcours();
        
      });
    }
    
    loadParcours(): void {
      const formationId = localStorage.getItem('forid'); // Corrected key name
      console.log('Formation Id from local storage:', formationId);
      if (formationId) {
        this.dataService.getParcoursByFormationsId(+formationId).subscribe({
          next: (data) => {
            this.parcoursList = data; // Store the fetched parcours
            console.log('Fetched parcours:', this.parcoursList);
          },
          error: (error) => {
            console.error('Error fetching parcours:', error);
          }
        });
      }
    }

    getImageUrlWithToken(imageUrl: String): string {
      const token = 'sp=racwd&st=2024-06-13T08:28:04Z&se=2026-11-08T16:28:04Z&spr=https&sv=2022-11-02&sr=c&sig=3Cu7JmlZU7vZdFEkVlVamz65Um87kmjIaZvjKTSG2AM%3D';
      return `${imageUrl}?${token}`;
    }
  


    loadSteps(): void {
    if (this.studentId) {
      this.dataService.getStepsWithCompletionStatus(this.studentId, this.selectedParcours).subscribe(data => {
        this.steps = data.map(stepWithStatus => ({
          ...stepWithStatus,
          category: '', // Replace with actual category logic
          startTime: stepWithStatus.startTime, // Ensure startTime is included
          endTime: null,
        }));
        console.log('Loaded steps:', this.steps);
      });
    }
  }
  
  selectParcours(parcoursId: number): void {
    this.selectedParcours = parcoursId;
    this.loadSteps(); // Reload steps for the selected parcours
  }

  
    confirmStart(step: Step): void {
      this.currentStep = step; // Set the current step
      this.showConfirmationModal = true; // Show the modal
    }
  
    deactivateAccount(): void {
     if (this.currentStep) {
      this.startStep(this.currentStep);
    }
      this.showConfirmationModal = false; // Close the modal after action
    }
  
    cancelDeactivation(): void {
      this.showConfirmationModal = false; // Close the modal without action
    }
  
    startStep(step: Step): void {
      this.dataService.startStep(this.studentId, step.id).subscribe(updatedStep => {
        
        step.startTime = updatedStep.startTime;
        step.completed = updatedStep.completed;
        this.router.navigate([`/learning-doing/${step.id}`]);
        console.log('Started step:', step);
        this.loadSteps();
      });
    }
  
    isStepAccessible(index: number): boolean {
      if (index === 0) {
        return true; // First step is always accessible
      }
      return this.steps[index - 1].completed; // Check if the previous step is completed
    }
  
    isStepLocked(step: Step): boolean {
      const previousStepIndex = this.steps.findIndex(s => s.id === step.id) - 1;
      return previousStepIndex >= 0 && !this.steps[previousStepIndex].completed;
    }
  
    isStepStarted(step: Step): boolean {
      return step.startTime !== null ;
    }
  
    isStepLockedAfterFinishTime(step: Step): boolean {
      return step.status; // Replace with your actual logic for checking locked steps after finish time
    }
  
    navigateToStep(step: Step): void {
      if (!this.isStepLocked(step) && !this.isStepLockedAfterFinishTime(step)) {
        this.confirmStart(step); // Show confirmation modal
      }
    }
  
    navigateToStartedStep(step: Step): void {
      if (!this.isStepLocked(step) && !this.isStepLockedAfterFinishTime(step)) {
        this.router.navigate([`/learning-doing/${step.id}`]);
      }
    }

    
  }