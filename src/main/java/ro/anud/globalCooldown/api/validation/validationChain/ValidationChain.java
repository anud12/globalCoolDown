package ro.anud.globalCooldown.api.validation.validationChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValidationChain<T> {

    private List<Function<T, Optional<ValidationChainResult>>> functionList;
    private final T object;

    public ValidationChain(final T object) {
        functionList = new ArrayList<>();
        this.object  = Objects.requireNonNull(object, "object must not be null");
    }

    public ValidationChain<T> check(Function<T, Optional<ValidationChainResult>> optionalFunction) {
        functionList.add(optionalFunction);
        return this;
    }

    public void validate(final Consumer<List<ValidationChainResult>> validationConsumer) {
        List<ValidationChainResult> validationChainResults = functionList.stream()
                .map(tBindingResultFunction -> tBindingResultFunction.apply(object))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (!validationChainResults.isEmpty()) {
            validationConsumer.accept(validationChainResults);
        }
    }
}
