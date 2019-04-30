package ro.anud.globalCooldown.api.exception;

import ro.anud.globalCooldown.api.validation.validationChain.ValidationChainResult;

import java.util.List;
import java.util.Objects;

public class TopicMessageException extends RuntimeException {
    private final List<ValidationChainResult> validationChainResultList;

    public TopicMessageException(final List<ValidationChainResult> validationChainResultList) {
        this.validationChainResultList = Objects.requireNonNull(validationChainResultList, "validationChainResultList must not be null");
    }

    public List<ValidationChainResult> getValidationChainResultList() {
        return validationChainResultList;
    }
}
