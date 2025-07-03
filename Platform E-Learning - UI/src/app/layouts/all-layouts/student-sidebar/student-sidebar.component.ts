import { Component } from '@angular/core';
import { SidebarToggleService } from '../../../services/sideBar/sidebar-toggle.service';

@Component({
  selector: 'app-student-sidebar',
  templateUrl: './student-sidebar.component.html',
  styleUrl: './student-sidebar.component.css'
})
export class StudentSidebarComponent {

  studentId : string | null = null;
  isVisible  = true;

  constructor(private sidebarToggleService: SidebarToggleService) {}

  ngOnInit() {
    this.sidebarToggleService.sidebarVisible$.subscribe(isVisible => {
      this.isVisible = isVisible;
    });
    this.studentId = localStorage.getItem('idoftheuser');

  }

}
