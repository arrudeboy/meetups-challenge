import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Location} from "../models/location";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  locationURL = 'http://localhost:8080/meetups-challenge-java-api/locations/';

  httpOptions = { headers: new HttpHeaders({'Content-Type' : 'application/json'})};

  constructor(private httpClient: HttpClient) { }

  public list(): Observable<Location[]> {
    // @ts-ignore
    return this.httpClient.get<Location[]>(this.locationURL + 'all', this.httpOptions);
  }

}
