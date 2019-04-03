import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {GenericValidatorService, ValidatorChain} from "../../generic-validator/generic-validator.service";
import {SecurityService} from "../security.service";
import {UserModel} from "../../java.models";
import {UserModelValidators} from "./userModelValidators";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    loginForm: UserModel = {
        username: ""
    };

    profileForm = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('')
    })

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

    formSubmit() {
        this.securityService.registerUser(this.loginForm).subscribe(value => {
            console.log(value)
        })
    }

    reactiveFormPress() {
        // console.log(this.profileForm.get("username").errors);
        // console.log(Validators.required.toString())
    }

    formKeyPress() {
        let errors = this.validatorChain.validate(this.loginForm);
        console.log(this.loginForm, errors);
        // console.log("KeyPress")
    }

    getMe() {
        this.securityService.getMe().subscribe(value => console.log(value))
    }

    doLogin() {
        this.securityService.loginUser(this.loginForm).subscribe((value: Response) => {
            console.log(value);
        })
    }
}
