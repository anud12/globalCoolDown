import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {GenericValidatorModule} from "../generic-validator/generic-validator.module";
import {HttpClientModule} from "@angular/common/http";
import {UserModelValidators} from "./login/userModelValidators";

@NgModule({
    declarations: [LoginComponent],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        GenericValidatorModule,
        HttpClientModule
    ],
    providers: [
        UserModelValidators
    ],
    exports: [
        LoginComponent
    ]
})
export class SecurityModule {
}
