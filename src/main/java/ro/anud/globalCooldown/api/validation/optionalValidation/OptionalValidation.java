package ro.anud.globalCooldown.api.validation.optionalValidation;

import org.springframework.stereotype.Service;

@Service
public class OptionalValidation {
    public OptionalValidationChain createChain() {
        return new OptionalValidationChain();
    }
}
