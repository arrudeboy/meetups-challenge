import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "../models/user";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userURL = 'http://localhost:8080/meetups-challenge-java-api/users/';

  httpOptions = { headers: new HttpHeaders({'Content-Type' : 'application/json'})};

  constructor(private httpClient: HttpClient) { }

  public list(): Observable<User[]> {
    // @ts-ignore
    return this.httpClient.get<User[]>(this.userURL + 'all', this.httpOptions);
  }

  public get(id: number): Observable<User> {
    // @ts-ignore
    return this.httpClient.get<User>(this.userURL + `${id}`, this.httpOptions);
  }

  public getByUsername(username: string): Observable<User> {
    let params = new HttpParams();
    params = params.append('username', username);
    this.httpOptions['params'] = params;
    // @ts-ignore
    return this.httpClient.get<User>(this.userURL + 'search', this.httpOptions);
  }

  public create(user: User): Observable<any> {
    // @ts-ignore
    return this.httpClient.post<any>(this.userURL + 'add', user, this.httpOptions);
  }

  public update(user?: User): Observable<any> {
    // @ts-ignore
    return this.httpClient.put<any>(this.userURL + 'update', user, this.httpOptions);
  }

  public delete(id?: number): Observable<any> {
    // @ts-ignore
    return this.httpClient.delete<any>(this.userURL + `${id}`, this.httpOptions);
  }

}
