import { Component } from '@angular/core';
import { SidebarToggleService } from '../../../services/sideBar/sidebar-toggle.service';

@Component({
  selector: 'app-admin-sidebar',
  templateUrl: './admin-sidebar.component.html',
  styleUrl: './admin-sidebar.component.css'
})
export class AdminSidebarComponent {

  isVisible  = true;

  constructor(private sidebarToggleService: SidebarToggleService) {}

  ngOnInit() {
    this.sidebarToggleService.sidebarVisible$.subscribe(isVisible => {
      this.isVisible = isVisible;
    });
  }

}
