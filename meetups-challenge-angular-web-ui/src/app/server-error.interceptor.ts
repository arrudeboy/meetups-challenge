import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {catchError, retry} from "rxjs/operators";

@Injectable()
export class ServerErrorInterceptor implements HttpInterceptor {

  // @ts-ignore
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // @ts-ignore
    return next.handle(request).pipe(
      // @ts-ignore
      retry(1),
      catchError((error: HttpErrorResponse) => {
        switch (error.status) {
          case 401: {
            // refresh token
            return null;
          }
          default:
            return throwError(error);
        }
      })
    );
  }
}
