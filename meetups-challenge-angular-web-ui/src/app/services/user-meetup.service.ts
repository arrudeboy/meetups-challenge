import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserMeetup} from "../models/userMeetup";

@Injectable({
  providedIn: 'root'
})
export class UserMeetupService {

  userMeetupURL = 'http://localhost:8080/meetups-challenge-java-api/meetupUsers/';

  httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

  constructor(private httpClient: HttpClient) {
  }

  public userMeetups(username: string): Observable<UserMeetup[]> {
    // @ts-ignore
    return this.httpClient.get<UserMeetup[]>(this.userMeetupURL + `${username}` + '/meetups', this.httpOptions);
  }

  public checkIn(userMeetupId: number): Observable<UserMeetup> {
    // @ts-ignore
    return this.httpClient.put<UserMeetup>(this.userMeetupURL + `${userMeetupId}` + '/checkIn', this.httpOptions);
  }

  public enroll(userMeetup): Observable<UserMeetup> {
    // @ts-ignore
    return this.httpClient.post<UserMeetup>(this.userMeetupURL + '/enroll', userMeetup, this.httpOptions);

  }


}
