import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {WeatherReportDialogComponent} from "./weather-report-dialog.component";
import {WeatherReportDialogService} from "./weather-report-dialog.service";

@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule
  ],
  declarations: [
    WeatherReportDialogComponent
  ],
  exports: [WeatherReportDialogComponent],
  entryComponents: [WeatherReportDialogComponent],
  providers: [WeatherReportDialogService]
})

export class WeatherReportDialogModule {
}
