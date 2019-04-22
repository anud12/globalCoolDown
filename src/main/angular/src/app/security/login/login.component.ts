import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {GenericValidatorService, ValidatorChain} from "../../generic-validator/generic-validator.service";
import {SecurityService} from "../security.service";
import {UserModel} from "../../java.models";
import {UserModelValidators} from "./userModelValidators";
import {toGenericValidator} from "../../generic-validator/toReactiveValidator.function";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    loginForm: UserModel = {
        username: ""
    };
    validatorChain: ValidatorChain<UserModel>;

    constructor(private validatorService: GenericValidatorService,
                private fb: FormBuilder,
                private securityService: SecurityService,
                private userModelValidators: UserModelValidators) {
        this.validatorChain = this.validatorService
            .build<UserModel>()
            .check(userModelValidators.usernameLengthValidation)
    }

    ngOnInit() {
    }

    doRegister() {
        this.securityService.registerUser(this.loginForm);
    }

    formKeyPress() {
        let errors = this.validatorChain.validate(this.loginForm);
        console.log(this.loginForm, errors);
        console.log(Validators.max(2).toString())
        // console.log("KeyPress")
    }

    doLogin() {
        this.securityService.loginUser(this.loginForm);
    }
}
