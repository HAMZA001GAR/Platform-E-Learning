import { Component, OnInit } from '@angular/core';
import { FormationService } from '../../services/formations/formation.service';
import { Formation } from '../../models/formation';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-formations',
  templateUrl: './list-formations.component.html',
  styleUrl: './list-formations.component.css'
})
export class ListFormationsComponent implements OnInit {
  Listformations: Formation[] = [];
  public backendUrl = "http://localhost:8090";

  constructor(private formationService: FormationService, private router: Router) { }
  ngOnInit(): void {
    this.loadFormations();
  }

  loadFormations(): void {
    this.formationService.getFormations().subscribe(formations => {
      this.Listformations = formations;
    });
  }
  onDelete(id: number): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'This formation will be permanently deleted.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        this.formationService.deleteFormation(id).subscribe({
          next: () => {
            this.Listformations = this.Listformations.filter(formation => formation.id !== id);
            Swal.fire(
              'Deleted!',
              'The formation has been deleted.',
              'success'
            );
          },
          error: (error) => {
          console.error('Error deleting formation', error);
          Swal.fire('Error', 'Something went wrong. Try again later.', 'error');
        }
    });
  }
});
}

navigateToStudents(formationId: number): void {
  this.router.navigate(['/students', formationId]);
}
}
