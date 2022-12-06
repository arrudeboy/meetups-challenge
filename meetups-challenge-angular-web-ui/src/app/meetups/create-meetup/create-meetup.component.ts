import { Component, OnInit } from '@angular/core';
import {MeetupService} from "../../services/meetup.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {LoginService} from "../../services/login.service";
import {Location} from "../../models/location";
import {LocationService} from "../../services/location.service";

@Component({
  selector: 'app-create-meetup',
  templateUrl: './create-meetup.component.html',
  styleUrls: ['./create-meetup.component.css']
})
export class CreateMeetupComponent implements OnInit {

  users: User[] = []
  selectedUsers: User[] = []

  locations: Location[] = []
  selectedLocation: Location = null

  constructor(private meetupService: MeetupService,
              private userService: UserService,
              private locationService: LocationService,
              private loginService: LoginService,
              private router: Router,
              private datePipe: DatePipe) { }

  ngOnInit(): void {

    this.userService.list().subscribe(
      data => {
        this.users = data;
      }
    );

    this.locationService.list().subscribe(
      data => {
        this.locations = data;
      }
    )
  }

  onCreate(f: NgForm): void {
    const data = f.value;
    let description = data['description']
    let strDate = this.datePipe.transform(data['date'], 'yyyy-MM-dd');
    let strTime = data['time'] + ':00';
    let sendInvitationUserIds = this.selectedUsers.map(u => u.id);
    let ownerUsername = this.loginService.getUsername();
    let meetup = {'description': description, 'ownerUsername': ownerUsername, 'sendInvitationUserIds': sendInvitationUserIds, 'date': strDate, 'time': strTime, 'locationId': this.selectedLocation.id}
    this.meetupService.create(meetup).subscribe(
      data => {
        console.log(data);
        this.goBack();
      }
    );
  }

  goBack(): void {
    this.router.navigate(['/meetups']);
  }

}
