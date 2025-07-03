import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StepsService } from '../../services/steps/steps.service';
import { Step } from '../../models/step';
import { Learning } from '../../models/learning';
import { Doing } from '../../models/doing';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-step',
  templateUrl: './edit-step.component.html',
  styleUrls: ['./edit-step.component.css']
})
export class EditStepComponent implements OnInit {
  stepId!: number;
  step: Step | null = null;
  learning: Learning[] = [];
  doing: Doing[] = [];
  isLoading = true;
  isEditing = false;
  newUrl: string = '';

  isEditingTask = false;
  isEditingDescription = false;
  isEditingLearning = false;
  editTitle: string = 'Part 1: Learning';
  editTask: string = '';
  isEditingDoing = false;
  isEditingImage = false;

  editableStep: Partial<Step> = {};

  focusedItemIndex: number | null = null;
  focusedUrlIndex: number | null = null;

  public backendUrl = "http://localhost:8090";


  constructor(
    private route: ActivatedRoute, 
    private stepsService: StepsService, 
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam !== null) {
        this.stepId = +idParam;
        this.loadStepDetails(this.stepId);
      }
    });
  }

  loadStepDetails(stepId: number): void {
    this.stepsService.getStepWithLearningAndDoing(stepId).subscribe(
      (response: any) => {
        this.step = response.step;
        this.learning = response.learning;
        this.doing = response.doing;
        this.editableStep = { ...this.step };
        this.editTask = this.doing.length > 0 ? this.doing[0].task : '';
        this.isLoading = false;
      },
      (error) => {
        console.error('Error fetching step details:', error);
        this.isLoading = false;
      }
    );
  }
  getImageUrlWithToken(imageUrl?: String): string {
    const token = 'sp=racwd&st=2024-06-13T08:28:04Z&se=2026-11-08T16:28:04Z&spr=https&sv=2022-11-02&sr=c&sig=3Cu7JmlZU7vZdFEkVlVamz65Um87kmjIaZvjKTSG2AM%3D';
    return `${imageUrl}?${token}`;
  }

  startEditing(): void {
    this.isEditing = true;
  }

  toggleEditMode(): void {
    this.isEditing = !this.isEditing;
    if (!this.isEditing) {
      this.editTitle = this.editableStep.title || 'Part 1: Learning';
    }
  }

  toggleDoingEditMode(): void {
    this.isEditingDoing = !this.isEditingDoing;
    if (!this.isEditingDoing) {
      if (this.doing.length > 0) {
        this.doing[0].task = this.editTask;
      }
    }
  }

  toggleEditTask(): void {
    this.isEditingTask = !this.isEditingTask;
  }

  toggleEditDescription(): void {
    this.isEditingDescription = !this.isEditingDescription;
  }

  selectedFileName: string = '';
  selectedFile!: File;

  toggleEditImage(): void {
    this.isEditingImage = !this.isEditingImage;
  }
  onImageChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      this.selectedFileName = file.name; 
    }
  }

  toggleEditLearning(): void {
    this.isEditingLearning = !this.isEditingLearning;
  }

  addUrl(): void {
    if (this.newUrl.trim() !== '') {
      if (this.learning.length > 0) {
        this.learning[0].url.push(this.newUrl.trim());
      }
      this.newUrl = '';
    }
  }

  saveChanges(): void {
    if (this.step && this.learning && this.doing) {
      const parcoursId = localStorage.getItem('parcoursid');
      const updatedData = {
        description: this.editableStep.description,
        durationInMinutes: this.editableStep.durationInMinutes,
        stepId: this.stepId,
        imageUrl: this.editableStep.imageUrl,
        parcoursId: parcoursId ? +parcoursId : undefined,
        stepProcess: this.editableStep.stepProcess,
        title: this.editableStep.title,
        descriptionLearning: this.learning[0]?.description,
        learningId: this.learning[0]?.id, 
        stepsId: this.stepId,
        learningtitle: this.learning[0]?.title,
        url: this.learning[0]?.url, 
        doingId: this.doing[0]?.id, 
        stepsIdInDoing: this.stepId,
        task: this.doing[0]?.task,
      };

      this.stepsService.updateStepWithLearningAndDoing(this.stepId, updatedData).subscribe({
        next: () => {
          Swal.fire({
          title: 'Success',
          text: 'The step has been updated successfully!',
          icon: 'success',
          confirmButtonText: 'OK',
          customClass: {
            popup: 'modern-swal-popup',
            confirmButton: 'modern-swal-confirm-btn'
          }
          }).then(() => {
          this.isEditing = false;
          window.location.reload();
        });
      },
        error: (err) => {
        console.error('Error updating:', err);
        Swal.fire('Error', 'Something went wrong while updating.', 'error');
      }
    });
    } else {
      console.warn('Step, learning, or doing is not defined.');
    }
  }

  onFocus(itemIndex: number, urlIndex: number): void {
    this.focusedItemIndex = itemIndex;
    this.focusedUrlIndex = urlIndex;
  }

  onInputChange(itemIndex: number, urlIndex: number, event: any): void {
    if (this.focusedItemIndex !== null && this.focusedUrlIndex !== null) {
      setTimeout(() => {
        const textarea = document.querySelector(`textarea[ng-reflect-name='learning[${itemIndex}].url[${urlIndex}]']`) as HTMLTextAreaElement;
        if (textarea) {
          textarea.focus();
        }
      });
    }
  }

  trackByIndex(index: number): number {
    return index;
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
       Swal.fire({
                   title: 'Deleted!',
                   text: 'The step has been deleted successfully.',
                   icon: 'success',
                   timer: 2000,
                   showConfirmButton: false
                 });

        const parcoursId = localStorage.getItem('parcoursid');
        if (parcoursId) {
          this.router.navigate([`/steps/parcours/${parcoursId}`]);
        } else {
          console.error('No parcoursId found in local storage.');
        }
      },
      error => {
        console.error('Error deleting step:', error);
        Swal.fire('Error', 'Failed to delete the step.', 'error');
      }
    );
   }
      });
 }
}

