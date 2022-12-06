import {Component, HostListener, Inject} from '@angular/core';
import {User} from "../../models/user";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-users-table-dialog',
  templateUrl: './users-table-dialog.component.html',
  styleUrls: ['./users-table-dialog.component.css']
})
export class UsersTableDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: User[],
              private mdDialogRef: MatDialogRef<UsersTableDialogComponent>) {
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
