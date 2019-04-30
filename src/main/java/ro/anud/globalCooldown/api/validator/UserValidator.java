package ro.anud.globalCooldown.api.validator;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.data.model.UserModel;
import ro.anud.globalCooldown.api.service.UserService;
import ro.anud.globalCooldown.api.validation.validationChain.ValidationChainResult;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserValidator {
    private final UserService userService;

    public UserValidator(final UserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    public Optional<ValidationChainResult> usernameIsUnique(UserModel userModel) {
        if (!userService.notExists(userModel)) {
            return Optional.of(ValidationChainResult.builder()
                                       .errorCode("User already exists")
                                       .field("username")
                                       .build());
        } else {
            return Optional.empty();
        }
    }

    public Optional<ValidationChainResult> usernameNotEmpty(UserModel userModel) {
        if (0 >= userModel.getUsername().length()) {
            return Optional.of(ValidationChainResult.builder()
                                       .errorCode("Username is empty")
                                       .field("username")
                                       .build());
        }
        return Optional.empty();
    }
}
