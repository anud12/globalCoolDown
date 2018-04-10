import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";
import {AuthenticationModel} from "../../authentication/authentication.model";

@Component({
    selector: 'app-user-info',
    templateUrl: './user-info.component.html',
    styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

    authenticationModel: AuthenticationModel;

    constructor(private authenticationService: AuthenticationService) {
    }

    ngOnInit() {
        this.authenticationModel = this.authenticationService.getModel();
    }

    logout() {
        this.authenticationService.logout();
    }
}
