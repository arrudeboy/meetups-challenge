import {ChangeDetectionStrategy, Component, HostListener, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'app-weather-report-dialog',
  templateUrl: './weather-report-dialog.component.html',
  styleUrls: ['./weather-report-dialog.component.css']
})
export class WeatherReportDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: {
    title: string,
    countryCode: string,
    city: string,
    date: string,
    time: string,
    temperature: number,
    temperatureMax: number,
    temperatureMin: number
  }, private mdDialogRef: MatDialogRef<WeatherReportDialogComponent>) {
  }

  public onClose() {
    this.close(false);
  }

  public close(value) {
    this.mdDialogRef.close(value);
  }

  @HostListener("keydown.esc")
  public onEsc() {
    this.close(false);
  }

}
