package ro.anud.globalcooldown.condition;

import ro.anud.globalcooldown.entity.Pawn;

import java.util.Objects;
import java.util.function.Function;

public enum PawnLongAttributeExtractor {
    VALUE(Pawn::getValue);

    private Function<Pawn, Long> extractor;

    PawnLongAttributeExtractor(Function<Pawn, Long> extractor) {
        this.extractor = Objects.requireNonNull(extractor, "extractor must not be null");
    }

    public Long apply(Pawn pawn) {
        return extractor.apply(pawn);
    }
}
