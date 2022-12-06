import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {UsersTableDialogComponent} from "./users-table-dialog.component";
import {UsersTableDialogService} from "./users-table-dialog.service";
import {MatTableModule} from "@angular/material/table";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {BrowserModule} from "@angular/platform-browser";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";

@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    BrowserAnimationsModule,
    BrowserModule,
  ],
  declarations: [
    UsersTableDialogComponent
  ],
  exports: [UsersTableDialogComponent],
  entryComponents: [UsersTableDialogComponent],
  providers: [UsersTableDialogService]
})

export class UsersTableDialogModule {
}
