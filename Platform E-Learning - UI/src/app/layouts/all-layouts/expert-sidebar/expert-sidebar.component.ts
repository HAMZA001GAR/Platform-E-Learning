import { Component } from '@angular/core';
import { SidebarToggleService } from '../../../services/sideBar/sidebar-toggle.service';

@Component({
  selector: 'app-expert-sidebar',
  templateUrl: './expert-sidebar.component.html',
  styleUrl: './expert-sidebar.component.css'
})
export class ExpertSidebarComponent {

  isVisible  = true;

  constructor(private sidebarToggleService: SidebarToggleService) {}

  ngOnInit() {
    this.sidebarToggleService.sidebarVisible$.subscribe(isVisible => {
      this.isVisible = isVisible;
    });
  }

}
