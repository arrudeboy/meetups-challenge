import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Meetup} from "../models/meetup";
import {Observable} from "rxjs";
import {User} from "../models/user";
import {WeatherReport} from "../models/weatherReport";

@Injectable({
  providedIn: 'root'
})
export class MeetupService {

  meetupURL = 'http://localhost:8080/meetups-challenge-java-api/meetups/';

  httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

  constructor(private httpClient: HttpClient) {
  }

  public list(): Observable<Meetup[]> {
    // @ts-ignore
    return this.httpClient.get<Meetup[]>(this.meetupURL + 'all', this.httpOptions);
  }

  public enrolledUsers(id: number): Observable<User[]> {
    // @ts-ignore
    return this.httpClient.get<User[]>(this.meetupURL + `${id}` + '/enrolledUsers', this.httpOptions);
  }

  public toEnrollUserMeetups(userId: number): Observable<Meetup[]> {
    // @ts-ignore
    return this.httpClient.get<Meetup[]>(this.meetupURL + `${userId}` + '/toEnrollMeetups', this.httpOptions);
  }

  public beerPacksNeeded(id: number): Observable<number> {
    // @ts-ignore
    return this.httpClient.get<number>(this.meetupURL + `${id}` + '/beerPacksNeeded', this.httpOptions);
  }

  public weather(id: number): Observable<WeatherReport> {
    // @ts-ignore
    return this.httpClient.get<WeatherReport>(this.meetupURL + `${id}` + '/weather', this.httpOptions);
  }

  public create(meetup): Observable<any> {
    // @ts-ignore
    return this.httpClient.post<Meetup>(this.meetupURL + '/create', meetup, this.httpOptions);
  }

}
