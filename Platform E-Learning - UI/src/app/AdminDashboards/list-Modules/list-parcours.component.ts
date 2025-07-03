import { Component, OnInit } from '@angular/core';
import { Parcours } from '../../models/parcours';
import { ParcoursService } from '../../services/parcours/parcours.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-parcours',
  templateUrl: './list-parcours.component.html',
  styleUrls: ['./list-parcours.component.css']
})
export class ListParcoursComponent implements OnInit {
  ListParcours: Parcours[] = [];
  public backendUrl = "http://localhost:8090";

  constructor(private parcoursService: ParcoursService) {}

  ngOnInit(): void {
    this.loadParcours();
  }

  loadParcours(): void {
    this.parcoursService.getAllParcours().subscribe(parcours => {
      this.ListParcours = parcours;
    }, error => {
      console.error('Error loading parcours:', error);
    });
  }

  deleteParcours(id: number): void {
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
              this.parcoursService.deleteParcours(id).subscribe ( {
                next: () => {
                  this.ListParcours = this.ListParcours.filter ( parcours => parcours.id != id);
                   Swal.fire(
                                'Deleted!',
                                'The formation has been deleted.',
                                'success'
                              );
                },
                error: (error) => {
                  console.error ( 'Error deleting Modules', error);
                  Swal.fire('Error', 'Something went wrong. Try again later.', 'error');
                }
              });
            }
          });
        }

      }
