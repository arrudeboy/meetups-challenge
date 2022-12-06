import {Injectable} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {UsersTableDialogComponent} from "./users-table-dialog.component";
import {User} from "../../models/user";

@Injectable()
export class UsersTableDialogService {
  dialogRef: MatDialogRef<UsersTableDialogComponent>;

  constructor(private dialog: MatDialog) {
  }

  public open(users: User[]) {
    this.dialogRef = this.dialog.open(UsersTableDialogComponent, {
      data: users
    });
  }

}
