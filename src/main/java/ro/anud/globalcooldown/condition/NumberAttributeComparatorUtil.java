package ro.anud.globalcooldown.condition;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public enum NumberAttributeComparatorUtil {
    GREATER_THAN((number, number2) -> number > number2),
    LESS_THAN((number, number2) -> number < number2);

    private final BiPredicate<Long, Long> biPredicate;

    NumberAttributeComparatorUtil(BiPredicate<Long, Long> biPredicate) {
        this.biPredicate = Objects.requireNonNull(biPredicate, "biPredicate must not be null");
    }

    public Predicate<Long> compare(Long compare) {
        return (Long to) -> biPredicate.test(compare, to);
    }
}
