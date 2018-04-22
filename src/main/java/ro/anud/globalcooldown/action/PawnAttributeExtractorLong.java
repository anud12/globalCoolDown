package ro.anud.globalcooldown.action;

import ro.anud.globalcooldown.entity.Pawn;

import java.util.Objects;
import java.util.function.Function;

public enum PawnAttributeExtractorLong {
    VALUE(Pawn::getValue);

    private Function<Pawn, Long> extractor;

    PawnAttributeExtractorLong(Function<Pawn, Long> extractor) {
        this.extractor = Objects.requireNonNull(extractor, "extractor must not be null");
    }

    public Long apply(Pawn pawn) {
        return extractor.apply(pawn);
    }
}
