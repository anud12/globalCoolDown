import {Injectable} from '@angular/core';
import {ValidationErrors} from "@angular/forms";

@Injectable({
    providedIn: 'root'
})
export class GenericValidatorService {
    build<T>(): ValidatorChain<T> {
        return new ValidatorChain<T>()
    }
}

export type GenericValidator<T> = (object: T, validationErrors: ValidationErrors) => ValidationErrors;

export class ValidatorChain<T> {

    validatorFunctionList: Array<GenericValidator<T>> = [];

    constructor() {
    }

    check(validatorFunction: GenericValidator<T>): ValidatorChain<T> {
        this.validatorFunctionList.push(validatorFunction);
        return this;
    }

    validate(object: T): ValidationErrors {
        let validationErrors: ValidationErrors = {};
        this.validatorFunctionList.forEach(validationFunction => {
            validationErrors = validationFunction(object, validationErrors)

        });
        return validationErrors;
    }
}