import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { StepsService } from '../../services/steps/steps.service';
import { Step } from '../../models/step';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-steps',
  templateUrl: './steps.component.html',
  styleUrls: ['./steps.component.css']
})
export class StepsComponent implements OnInit {
  stepsList: Step[] = [];
  parcoursId!: number;
  countUser!: number;
  currentPage: number = 0;
  pageSize: number = 3; // Default page size
  totalPages: number = 0;
  screenWidth: number = window.innerWidth;
  public backendUrl = "http://localhost:8090";

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private stepsService: StepsService,
    private eRef: ElementRef,
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.parcoursId = +params['parcoursId'];
      this.loadSteps(this.parcoursId, this.currentPage);
    });

    // Add window resize event listener
    this.adjustPageSize();
  }

  loadSteps(parcoursId: number, page: number): void {
    this.stepsService.getStepsByParcoursId(parcoursId, page, this.pageSize).subscribe(
      (response) => {
        this.stepsList = response.content; 
        this.totalPages = response.totalPages;
        this.currentPage = response.number;
        this.loadUsers();
      },
      (error) => {
        console.error('Error fetching steps', error);
      }
    );
  }

  updatePagination(stepData: any): void {
    this.totalPages = stepData.totalPages;
    this.currentPage = stepData.currentPage;
  }

  onImageError(url: string) {
  console.error('Image not found at:', url);
}

  getImageUrlWithToken(imageUrl: string): string {
    const token = 'sp=racwd&st=2024-06-13T08:28:04Z&se=2026-11-08T16:28:04Z&spr=https&sv=2022-11-02&sr=c&sig=3Cu7JmlZU7vZdFEkVlVamz65Um87kmjIaZvjKTSG2AM%3D';
    return `${imageUrl}?${token}`;
  }

  loadCounts(): void {
    this.stepsService.getStepsCount().subscribe(
      (counts) => {
        this.stepsList.forEach(step => {
          const countData = counts.find((c: any) => c[0] === step.id);
          if (countData) {
            step.count = countData[1];
            step.progress = countData ? (step.count / this.countUser) * 100 : 0;
          }
        });
      },
      (error) => {
        console.error('Error fetching counts', error);
      }
    );
  }

  loadUsers(): void {
    this.stepsService.getCountUsersByParcoursId(this.parcoursId).subscribe(
      countUsers => {
        this.countUser = countUsers;
        this.loadCounts();
      },
      error => {
        console.error('Error fetching users', error);
      }
    );
  }

  navigateToTask(): void {
    this.router.navigate(['/task']);
  }

  openStepId: number | null = null;

  toggleDropdown(stepId: number) {
    this.openStepId = this.openStepId === stepId ? null : stepId;
  }

  @HostListener('document:click', ['$event'])
  clickout(event: any) {
    if (this.openStepId && !this.eRef.nativeElement.contains(event.target)) {
      this.openStepId = null;
    }
  }

 deleteStep(stepId: number): void {
  Swal.fire({
    title: 'Are you sure?',
    text: 'This action will permanently delete the step.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Yes, delete it',
    cancelButtonText: 'Cancel',
    customClass: {
      popup: 'modern-swal-popup',
      confirmButton: 'modern-swal-confirm-btn',
      cancelButton: 'modern-swal-cancel-btn'
    }
  }).then((result) => {
    if (result.isConfirmed) {
      this.stepsService.DeleteStepsById(stepId).subscribe(
        () => {
          this.stepsList = this.stepsList.filter(step => step.id !== stepId);
          Swal.fire({
            title: 'Deleted!',
            text: 'The step has been deleted successfully.',
            icon: 'success',
            timer: 2000,
            showConfirmButton: false
          });
        },
        error => {
          console.error('Error deleting step:', error);
          Swal.fire('Error', 'Something went wrong while deleting.', 'error');
        }
      );
    }
  });
}

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    this.adjustPageSize();
  }

  adjustPageSize(): void {
    const screenWidth = window.innerWidth;
    this.screenWidth = screenWidth;

    if (screenWidth <= 1163) {
      this.pageSize = 3;
    } else if (1164 <= screenWidth && screenWidth <= 1280) {
      this.pageSize = 4;
    } else if (1281 <= screenWidth && screenWidth <= 1422) {
      this.pageSize = 3;
    } else if (1423 <= screenWidth && screenWidth <= 1600) {
      this.pageSize = 6;
    } else if (1601 <= screenWidth && screenWidth <= 1707) {
      this.pageSize = 7;
    } else if (1708 <= screenWidth && screenWidth <= 1920) {
      this.pageSize = 8;
    }

    // Reload steps with the new pageSize
    this.loadSteps(this.parcoursId, this.currentPage);
  }

  handleNext() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadSteps(this.parcoursId, this.currentPage);
    }
  }

  handlePrevious() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadSteps(this.parcoursId, this.currentPage);
    }
  }
}
