import { Component, OnInit } from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {LoginService} from "../../services/login.service";
import {ConfirmDialogService} from "../../shared/confirm-dialog/confirm-dialog.service";

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit {

  users: User[] = [];

  isAdmin: boolean | undefined;

  constructor(private userService: UserService, private loginService: LoginService, private dialogService: ConfirmDialogService) { }

  ngOnInit(): void {
    this.loadUsers();
    this.isAdmin = this.loginService.getIsAdmin();
  }

  loadUsers(): void {
    this.userService.list().subscribe(
      data => {
        this.users = data;
      }
    );
  }

  onDelete(id?: number): void {

    const options = {
      title: 'User delete?',
      message: 'Are you sure you want to delete this user?',
      cancelText: 'CANCEL',
      confirmText: 'YES, DELETE USER'
    };

    this.dialogService.open(options);

    this.dialogService.confirmed().subscribe(confirmed => {
      if (confirmed) {
        this.deleteUser(id);
      }
    });
  }

  private deleteUser(id?: number): void {
    this.userService.delete(id).subscribe(
      data => {
        console.log(data);
        this.loadUsers();
      }
    );
  }

}
