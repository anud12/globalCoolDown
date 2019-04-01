import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ValidationChainBuilder} from "./ValidatorChain";
import {LoginValidators} from "./login/login.validators";
import {GenericValidatorModule} from "../generic-validator/generic-validator.module";

@NgModule({
    declarations: [LoginComponent],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        GenericValidatorModule
    ],
    providers: [
        LoginValidators
    ],
    exports: [
        LoginComponent
    ]
})
export class SecurityModule {
}
