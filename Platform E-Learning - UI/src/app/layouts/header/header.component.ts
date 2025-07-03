import { Component, ElementRef, HostListener } from '@angular/core';
import { SidebarToggleService } from '../../services/sideBar/sidebar-toggle.service';
import { Router } from '@angular/router';
import { AuthServiceService } from '../../services/authentication/auth-service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  constructor(private sidebarToggleService: SidebarToggleService,
    private router: Router,
    private authServiceService: AuthServiceService,
    private eRef: ElementRef) { }

  toggleSidebar() {
    this.sidebarToggleService.toggleSidebar();
  }
  isMenuOpen = false;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  @HostListener('document:click', ['$event'])
  clickout(event: any) {
    if (this.isMenuOpen && !this.eRef.nativeElement.contains(event.target)) {
      this.isMenuOpen = false;
    }
  }

  logout() {
    this.authServiceService.logout();

  }
}
