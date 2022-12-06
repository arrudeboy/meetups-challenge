import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {ConfirmDialogService} from './confirm-dialog.service';
import {ConfirmDialogComponent} from './confirm-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule
  ],
  declarations: [
    ConfirmDialogComponent
  ],
  exports: [ConfirmDialogComponent],
  entryComponents: [ConfirmDialogComponent],
  providers: [ConfirmDialogService]
})

export class ConfirmDialogModule {
}
