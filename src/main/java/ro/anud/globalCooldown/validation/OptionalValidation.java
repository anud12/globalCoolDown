package ro.anud.globalCooldown.validation;

import org.springframework.stereotype.Service;

@Service
public class OptionalValidation {
    public OptionalValidationChain createChain() {
        return new OptionalValidationChain();
    }
}
