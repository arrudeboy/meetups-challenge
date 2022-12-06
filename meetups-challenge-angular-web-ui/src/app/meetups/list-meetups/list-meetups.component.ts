import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Meetup} from "../../models/meetup";
import {LoginService} from "../../services/login.service";
import {MeetupService} from "../../services/meetup.service";
import {WeatherReportDialogService} from "../../shared/weather-report-dialog/weather-report-dialog.service";
import {UserMeetupService} from "../../services/user-meetup.service";
import {UserMeetup} from "../../models/userMeetup";
import {Router} from "@angular/router";
import {UsersTableDialogService} from "../../shared/users-table-dialog/users-table-dialog.service";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-list-meetups',
  templateUrl: './list-meetups.component.html',
  styleUrls: ['./list-meetups.component.css']
})
export class ListMeetupsComponent implements OnInit {

  isAdmin: boolean | undefined;

  meetups: Meetup[] = [];
  userMeetups: UserMeetup[] = [];
  beerPacksNeeded: number = 0;

  constructor(private router: Router,
              private loginService: LoginService,
              private meetupService: MeetupService,
              private userMeetupService: UserMeetupService,
              private dialog: MatDialog,
              private weatherReportDialogService: WeatherReportDialogService,
              private usersTableDialogService: UsersTableDialogService) { }

  ngOnInit(): void {
    this.isAdmin = this.loginService.getIsAdmin();
    this.loadMeetups();
  }

  private loadMeetups(): void {
    if(this.isAdmin) {
      this.meetupService.list().subscribe(
        data => {
          this.meetups = data;
        }
      );
    } else {
      this.userMeetupService.userMeetups(this.loginService.getUsername()).subscribe(
        data => {
          this.userMeetups = data;
        }
      );
    }
  }

  onRequestWeather(meetupId: number) {
    this.meetupService.weather(meetupId).subscribe(
      res => {
        console.log(res);
        const data = {
          title: 'Meetup Weather Report',
          city: res.city,
          countryCode: res.countryCode,
          date: res.date,
          time: res.time,
          temperature: res.temperature,
          temperatureMax: res.temperatureMax,
          temperatureMin: res.temperatureMin
        }
        this.weatherReportDialogService.open(data);
      }
    )
  }

  onEnrolledUsers(meetupId: number) {

    this.meetupService.enrolledUsers(meetupId).subscribe(
      res => {
        this.usersTableDialogService.open(res);
      }
    )
  }

  onBeerPacksNeeded(meetupId: number) {

    this.meetupService.beerPacksNeeded(meetupId).subscribe(
      res => {
        this.beerPacksNeeded = res['beerPacksNeeded'];
      }
    )
  }

  openBeerPacksNeededModal(templateRef, meetupId: number) {
    this.onBeerPacksNeeded(meetupId);
    this.dialog.open(templateRef, {
      width: '400px',
    });
  }

  closeBeerPacksNeededModal() {
    this.dialog.closeAll();
  }

  onMeetupCheckIn(userMeetupId: number) {
    this.userMeetupService.checkIn(userMeetupId).subscribe(
      res => {
        this.loadMeetups();
      }
    )
  }

  goBack(): void {
    this.router.navigate(['/meetups']);
  }

}
