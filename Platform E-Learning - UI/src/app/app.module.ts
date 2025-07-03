import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './layouts/header/header.component';
import { EditStepComponent } from './ExpertDashboards/edit-step/edit-step.component';
import { AddStepComponent } from './ExpertDashboards/add-step/add-step.component';
import { ParcoursProgressComponent } from './StudentDashboards/parcours-progress/parcours-progress.component';
import { StudentProfileComponent } from './StudentDashboards/student-profile/student-profile.component';
import { LearningDoingComponent } from './StudentDashboards/learning-doing/learning-doing.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ParcoursComponent } from './ExpertDashboards/parcours/parcours.component';
import { AddLearningComponent } from './ExpertDashboards/add-learning/add-learning.component';
import { AddDoingComponent } from './ExpertDashboards/add-doing/add-doing.component';
import { StepsComponent } from './ExpertDashboards/steps-card/steps.component';
import { SignUpComponent } from './Auth/sign-up/sign-up.component';
import { SignInComponent } from './Auth/sign-in/sign-in.component';
import { PasswordRecoveryComponent } from './Auth/password-recovery/password-recovery.component';
import { AllLayoutsComponent } from './layouts/all-layouts/all-layouts.component';
import { JwtModule } from '@auth0/angular-jwt';
import { JwtInterceptor } from './Auth/jwt.interceptor';
import { AdminSidebarComponent } from './layouts/all-layouts/admin-sidebar/admin-sidebar.component';
import { ExpertSidebarComponent } from './layouts/all-layouts/expert-sidebar/expert-sidebar.component';
import { StudentSidebarComponent } from './layouts/all-layouts/student-sidebar/student-sidebar.component';
import { ManageParcoursComponent } from './AdminDashboards/manage-Modules/manage-parcours.component';
import { ManageFormationsComponent } from './AdminDashboards/manage-formations/manage-formations.component';
import { AnalyticsComponent } from './ExpertDashboards/analytics/analytics.component';
import { DataTableModule } from '@bhplugin/ng-datatable';
import { CommonModule } from '@angular/common';
import { UserServiceService } from './services/userService/user-service.service';
import { StudentProgressComponent } from './ExpertDashboards/student-progress/student-progress.component';
import { StudentInAStepComponent } from './ExpertDashboards/student-in-astep/student-in-astep.component';
import { ListFormationsComponent } from './AdminDashboards/list-formations/list-formations.component';
import { ListParcoursComponent } from './AdminDashboards/list-Modules/list-parcours.component';
import { ListOfStudentsInParcoursComponent } from './AdminDashboards/list-of-students-in-parcours/list-of-students-in-parcours.component';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    EditStepComponent,
    AddStepComponent,
    ParcoursProgressComponent,
    StudentProfileComponent,
    LearningDoingComponent,
    ParcoursComponent,
    AddLearningComponent,
    AddDoingComponent,
    StepsComponent,
    SignUpComponent,
    SignInComponent,
    PasswordRecoveryComponent,
    AllLayoutsComponent,
    AdminSidebarComponent,
    ExpertSidebarComponent,
    StudentSidebarComponent,
    ManageParcoursComponent,
    ManageFormationsComponent,
    AnalyticsComponent,
    StudentProgressComponent,
    StudentInAStepComponent,
    ListFormationsComponent,
    ListParcoursComponent,
    ListOfStudentsInParcoursComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatInputModule,
    MatFormFieldModule,
    DataTableModule,
    CommonModule,
    MatDialogModule,
    SweetAlert2Module.forRoot(),
    RouterModule,
   
    JwtModule.forRoot({
      config: {
        disallowedRoutes: ['http://localhost:4200/api/auth/'],
      },
    })
  ],
  providers: [provideAnimationsAsync(),
  { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true } , UserServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }

