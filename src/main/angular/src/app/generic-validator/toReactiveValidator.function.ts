import {GenericValidator} from "./generic-validator.service";
import {AbstractControl, ValidatorFn} from "@angular/forms";

export const toGenericValidator = <T>(onFail: GenericValidator<T>,
                                      reactValidator: ValidatorFn,
                                      transformForReact: (object: T) => any = T => T,
): GenericValidator<T> => {
    return (object, validationErrors) => {
        const abstractControl = {
            value: transformForReact(object)
        };
        const reactErrors = reactValidator(abstractControl as AbstractControl);
        if (reactErrors != null) {
            return onFail(object, validationErrors)
        }
        return validationErrors;
    };
}