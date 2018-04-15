package ro.anud.globalcooldown.condition;

import ro.anud.globalcooldown.entity.Pawn;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class ValueCondition implements ConditionOnPawn {


    private Function<Pawn, Number> extractor;
    private BiPredicate<Number, Number> predicate;
    private Number value;

    public ValueCondition(final Function<Pawn, Number> extractor,
                          final BiPredicate<Number, Number> predicate,
                          Number value) {
        this.extractor = Objects.requireNonNull(extractor, "extractor must not be null");
        this.predicate = Objects.requireNonNull(predicate, "predicate must not be null");
        this.value = Objects.requireNonNull(value, "value must not be null");
    }

    @Override
    public boolean test(Pawn pawn) {
        return predicate.test(extractor.apply(pawn), value);
    }
}
