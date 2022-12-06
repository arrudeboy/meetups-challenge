import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../services/user.service";
import {LoginService} from "../../services/login.service";
import {UserMeetupService} from "../../services/user-meetup.service";
import {Meetup} from "../../models/meetup";
import {MeetupService} from "../../services/meetup.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-enroll-meetups',
  templateUrl: './enroll-meetups.component.html',
  styleUrls: ['./enroll-meetups.component.css']
})
export class EnrollMeetupsComponent implements OnInit {

  userId: number = null;
  meetups: Meetup[] = [];

  constructor(private userService: UserService,
              private meetupService: MeetupService,
              private userMeetupService: UserMeetupService,
              private loginService: LoginService,
              private dialog: MatDialog,
              private router: Router) {
  }

  ngOnInit(): void {
    this.userService.getByUsername(this.loginService.getUsername()).subscribe(
      res => {
        this.userId = res.id;
        this.loadToEnrollUserMeetups();
      }
    )
  }

  loadToEnrollUserMeetups() {
    this.meetupService.toEnrollUserMeetups(this.userId).subscribe(
      res => {
        this.meetups = res;
      }
    )
  }

  onEnroll(meetupId): void {
    let userMeetup = {'userId': this.userId, 'meetupId': meetupId}
    this.userMeetupService.enroll(userMeetup).subscribe(
      res => {
        this.loadToEnrollUserMeetups();
      }
    )
  }

  openEnrollModal(templateRef, meetupId: number) {
    this.onEnroll(meetupId);
    this.dialog.open(templateRef, {
      width: '400px',
    });
  }

  closeEnrollModal() {
    this.dialog.closeAll();
  }

  closeEnrollModalAndRedirect() {
    this.dialog.closeAll();
    this.router.navigate(['/meetups']);
  }

}
