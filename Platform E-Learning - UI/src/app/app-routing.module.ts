import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { EditStepComponent } from './ExpertDashboards/edit-step/edit-step.component';
import { AddStepComponent } from './ExpertDashboards/add-step/add-step.component';
import { ParcoursProgressComponent } from './StudentDashboards/parcours-progress/parcours-progress.component';
import { StudentProfileComponent } from './StudentDashboards/student-profile/student-profile.component';
import { LearningDoingComponent } from './StudentDashboards/learning-doing/learning-doing.component';
import { ParcoursComponent } from './ExpertDashboards/parcours/parcours.component';
import { AddLearningComponent } from './ExpertDashboards/add-learning/add-learning.component';
import { AddDoingComponent } from './ExpertDashboards/add-doing/add-doing.component';
import { StepsComponent } from './ExpertDashboards/steps-card/steps.component';
import { SignUpComponent } from './Auth/sign-up/sign-up.component';
import { SignInComponent } from './Auth/sign-in/sign-in.component';
import { PasswordRecoveryComponent } from './Auth/password-recovery/password-recovery.component';
import { AllLayoutsComponent } from './layouts/all-layouts/all-layouts.component';
import { AuthGuard } from './guards/authGuard/authguard.guard';
import { roleguardGuard } from './guards/roleGuard/roleguard.guard';
import { ManageParcoursComponent } from './AdminDashboards/manage-Modules/manage-parcours.component';
import { ManageFormationsComponent } from './AdminDashboards/manage-formations/manage-formations.component';
import { AnalyticsComponent } from './ExpertDashboards/analytics/analytics.component';
import { StudentProgressComponent } from './ExpertDashboards/student-progress/student-progress.component';
import { StudentInAStepComponent } from './ExpertDashboards/student-in-astep/student-in-astep.component';
import { ListFormationsComponent } from './AdminDashboards/list-formations/list-formations.component';
import { ListParcoursComponent } from './AdminDashboards/list-Modules/list-parcours.component';
import { ListOfStudentsInParcoursComponent } from './AdminDashboards/list-of-students-in-parcours/list-of-students-in-parcours.component';


const routes: Routes = [


  { path: '', redirectTo: '/signin', pathMatch: 'full' },
  {
    path: "",
    component: AllLayoutsComponent,
    children: [

      { path: ':userId/step-progress', component: ParcoursProgressComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['STUDENT'] } },
      { path: 'student-profile', component: StudentProfileComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['STUDENT'] } },
      { path: 'learning-doing/:id', component: LearningDoingComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['STUDENT'] } },



      { path: 'steps/parcours/:parcoursId', component: StepsComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'edit-step/:id', component: EditStepComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'add-step', component: AddStepComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'student-progress', component: StudentProgressComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'parcours', component: ParcoursComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'add-learning', component: AddLearningComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'add-doing', component: AddDoingComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'analytics', component: AnalyticsComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'student-progress/:id', component: StudentProgressComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },
      { path: 'student-step/:stepId', component: StudentInAStepComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['EXPERT'] } },

      

      { path: 'manage-parcours', component: ManageParcoursComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['ADMIN'] } },
      { path: 'manage-formations', component: ManageFormationsComponent, canActivate: [AuthGuard, roleguardGuard], data: { roles: ['ADMIN'] } },
      { path:'list-formations',component:ListFormationsComponent,canActivate: [AuthGuard, roleguardGuard], data: { roles: ['ADMIN'] }},
      { path:'list-parcours',component:ListParcoursComponent,canActivate: [AuthGuard, roleguardGuard], data: { roles: ['ADMIN'] }},
      { path: 'students/:formationId', component: ListOfStudentsInParcoursComponent,canActivate: [AuthGuard, roleguardGuard], data: { roles: ['ADMIN'] }}
    ],
  },

  { path: "signup",component: SignUpComponent, title: "signupPage"},
  { path: 'signin', component: SignInComponent },
  { path: 'recoverPassword', component: PasswordRecoveryComponent }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }