import { Component, OnInit } from '@angular/core';
import {User} from "../../models/user";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  user: User;

  constructor(
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    console.log(id);
    this.userService.get(id).subscribe(
      data => {
        console.log(data);
        this.user = data;
        console.log(this.user);
      }
    );
  }

  onUpdate(f: NgForm): void {
    let data = f.value;
    this.user.email = data['email'];
    this.user.password = data['password']
    this.userService.update(this.user).subscribe(
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
