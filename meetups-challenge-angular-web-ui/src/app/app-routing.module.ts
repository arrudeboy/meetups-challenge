import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {NgModule} from "@angular/core";
import {ListUsersComponent} from "./users/list-users/list-users.component";
import {CreateUserComponent} from "./users/create-user/create-user.component";
import {UpdateUserComponent} from "./users/update-user/update-user.component";
import {AdminGuard} from "./guards/admin.guard";
import {ListMeetupsComponent} from "./meetups/list-meetups/list-meetups.component";
import {CreateMeetupComponent} from "./meetups/create-meetup/create-meetup.component";
import {EnrollMeetupsComponent} from "./meetups/enroll-meetups/enroll-meetups.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'users', component: ListUsersComponent},
  {path: 'users/create', component: CreateUserComponent, canActivate: [AdminGuard], data: {requiredRoles: ['meetup_admin']}},
  {path: 'users/update/:id', component: UpdateUserComponent, canActivate: [AdminGuard], data: {requiredRoles: ['meetup_admin']}},
  {path: 'meetups', component: ListMeetupsComponent},
  {path: 'meetups/create', component: CreateMeetupComponent, canActivate: [AdminGuard], data: {requiredRoles: ['meetup_admin']}},
  {path: 'meetups/enroll', component: EnrollMeetupsComponent, canActivate: [AdminGuard], data: {requiredRoles: ['meetup_user']}},
  {path: '**', redirectTo: '', pathMatch: 'full'}
]

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})

export class AppRoutingModule {}
