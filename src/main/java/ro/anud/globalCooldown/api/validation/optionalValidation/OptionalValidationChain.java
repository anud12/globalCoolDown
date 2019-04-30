package ro.anud.globalCooldown.api.validation.optionalValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalValidationChain {
    private List<Optional> optionalList;

    public OptionalValidationChain() {
        optionalList = new ArrayList<>();
    }

    public OptionalValidationChain validate(final Optional optional) {
        optionalList.add(optional);
        return this;
    }

    public boolean isAnyNotPresent() {
        return optionalList.stream()
                .anyMatch(optional -> !optional.isPresent());
    }

}
