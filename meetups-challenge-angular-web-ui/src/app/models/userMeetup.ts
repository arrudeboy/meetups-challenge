import {Meetup} from "./meetup";

export class UserMeetup {

  id?: number;
  meetup: Meetup;
  checkIn: boolean;

  constructor(meetup: Meetup, checkIn: boolean, id?: number) {
    this.id = id;
    this.meetup = meetup;
    this.checkIn = checkIn;
  }

}
