import {Injectable} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {WeatherReportDialogComponent} from "./weather-report-dialog.component";

@Injectable()
export class WeatherReportDialogService {
  dialogRef: MatDialogRef<WeatherReportDialogComponent>;

  constructor(private dialog: MatDialog) {
  }

  public open(options) {
    this.dialogRef = this.dialog.open(WeatherReportDialogComponent, {
      data: {
        title: options.title,
        countryCode: options.countryCode,
        city: options.city,
        date: options.date,
        time: options.time,
        temperature: options.temperature,
        temperatureMax: options.temperatureMax,
        temperatureMin: options.temperatureMin
      }
    });
  }

}
