import { Component, OnInit } from '@angular/core';
import { Parcours } from '../../models/parcours';
import { ParcoursService } from '../../services/parcours/parcours.service';

@Component({
  selector: 'app-parcours',
  templateUrl: './parcours.component.html',
  styleUrl: './parcours.component.css'
})
export class ParcoursComponent  {

  parcoursList: Parcours[] = [];

  parcoursAllList : Parcours[] = [];

  public backendUrl = "http://localhost:8090";

  constructor(private parcoursService: ParcoursService) { }

  ngOnInit(): void {
    this.loadParcours();
    this.loadAllParcours();
  }

  getImageUrlWithToken(imageUrl: String): string {
    const token = 'sp=racwd&st=2024-06-13T08:28:04Z&se=2026-11-08T16:28:04Z&spr=https&sv=2022-11-02&sr=c&sig=3Cu7JmlZU7vZdFEkVlVamz65Um87kmjIaZvjKTSG2AM%3D';
    return `${imageUrl}?${token}`;
  }

  loadParcours() {
    const parcoursId = localStorage.getItem('parcoursid');
    if (parcoursId) {
      this.parcoursService.getParcoursById(parcoursId).subscribe(
        (data) => {
          this.parcoursList = [data];
          console.log(this.parcoursList);
        },
        (error) => {
          console.error('Error fetching parcours', error);
        }
      );
    } else {
      console.error('No parcoursId found in local storage');
    }
  }
  
  loadAllParcours() {
    
      this.parcoursService.getAllParcours().subscribe(
        
      );
   
  }

}


