import {LoginForm} from "./login.component";
import {Validators} from "@angular/forms";
import {toGenericValidator} from "../../generic-validator/toReactiveValidator.function";
import {GenericValidator} from "../../generic-validator/generic-validator.service";

export class LoginValidators {
    usernameLengthValidation: GenericValidator<LoginForm> =
        toGenericValidator((object, validationErrors) => {
                validationErrors["username"] = "email";
                return validationErrors;
            },
            Validators.email,
            object => object.username
        )
}