import {Component} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthenticationService} from "../authentication.service";
import {LoginModel} from "./login.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  public username: string = "";
  public error: string = undefined;

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  onSubmit($event) {
    $event.preventDefault();
    $event.stopImmediatePropagation();
    this.authenticationService.signInUser(new LoginModel(this.username))
      .subscribe(result => {
          if (result === true) {
            this.error = undefined;
            this.router.navigate(["/"]);
          } else {
            this.error = "Invalid name";
          }
        },
        error => {
          console.warn(error);
          this.error = "Invalid name"
        });
  }


}
