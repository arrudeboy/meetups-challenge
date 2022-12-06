import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  constructor(
    private userService: UserService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
  }

  onCreate(f: NgForm): void {
    const data = f.value;
    const user: User = new User(data['username'], data['email'], data['password']);
    this.userService.create(user).subscribe(
      data => {
        console.log(data);
        this.goBack();
      }
    );
  }

  goBack(): void {
    this.router.navigate(['/users']);
  }

}
