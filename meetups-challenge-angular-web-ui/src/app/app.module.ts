import {ErrorHandler, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { OAuthModule } from 'angular-oauth2-oidc';
import {FormsModule, ReactiveFormsModule} from '@angular/forms'

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from "./app-routing.module";
import { MenuComponent } from './menu/menu.component';
import { ListUsersComponent } from './users/list-users/list-users.component';
import { CreateUserComponent } from './users/create-user/create-user.component';
import { UpdateUserComponent } from './users/update-user/update-user.component';
import { EqualValidator } from './directives/equal-validator.directive';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {GlobalErrorHandler} from "./global-error-handler";
import {ServerErrorInterceptor} from "./server-error.interceptor";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {ConfirmDialogModule} from "./shared/confirm-dialog/confirm-dialog.module";
import { ListMeetupsComponent } from './meetups/list-meetups/list-meetups.component';
import {WeatherReportDialogModule} from "./shared/weather-report-dialog/weather-report-dialog.module";
import {UsersTableDialogModule} from "./shared/users-table-dialog/users-table-dialog.module";
import { CreateMeetupComponent } from './meetups/create-meetup/create-meetup.component';
import {DatePipe} from "@angular/common";
import {NgSelectModule} from "@ng-select/ng-select";
import { EnrollMeetupsComponent } from './meetups/enroll-meetups/enroll-meetups.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MenuComponent,
    ListUsersComponent,
    CreateUserComponent,
    UpdateUserComponent,
    EqualValidator,
    ListMeetupsComponent,
    CreateMeetupComponent,
    EnrollMeetupsComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgSelectModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    ConfirmDialogModule,
    WeatherReportDialogModule,
    UsersTableDialogModule,
    OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: ['http://localhost:8080/meetups-challenge-java-api'],
        sendAccessToken: true
      }
    })
  ],
  providers: [
    DatePipe,
    { provide: ErrorHandler, useClass: GlobalErrorHandler },
    { provide: HTTP_INTERCEPTORS, useClass: ServerErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
