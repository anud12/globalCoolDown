import {Validators} from "@angular/forms";
import {toGenericValidator} from "../../generic-validator/toReactiveValidator.function";
import {GenericValidator} from "../../generic-validator/generic-validator.service";
import {UserModel} from "../../java.models";

export class UserModelValidators {
    usernameLengthValidation: GenericValidator<UserModel> =
        toGenericValidator((object, validationErrors) => {
                validationErrors["username"] = "email";
                return validationErrors;
            },
            Validators.email,
            object => object.username
        )
}