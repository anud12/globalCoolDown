import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginValidators} from "./login.validators";
import {GenericValidatorService, ValidatorChain} from "../../generic-validator/generic-validator.service";

export class LoginForm {
    username: string;

    constructor() {
        this.username = ""
    }
}

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    loginForm: LoginForm = {
        username: ""
    };

    profileForm = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('')
    })

    validatorChain: ValidatorChain<LoginForm>;

    constructor(private validatorService: GenericValidatorService,
                private fb: FormBuilder,
                private loginValidators: LoginValidators) {
        this.validatorChain = this.validatorService
            .build<LoginForm>()
            .check(loginValidators.usernameLengthValidation)
    }

    ngOnInit() {
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
}
