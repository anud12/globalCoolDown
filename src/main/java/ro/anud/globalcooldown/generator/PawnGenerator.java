package ro.anud.globalcooldown.generator;

import ro.anud.globalcooldown.entity.Pawn;

import java.util.Objects;
import java.util.function.Supplier;

public enum PawnGenerator {
    PAWN(() -> Pawn.builder()
            .name("Pawn")
            .value(0L)
            .characterCode(80L)
            .speed(100L)
            .version(0L)
            .build());

    private Supplier<Pawn> pawnSupplier;

    PawnGenerator(final Supplier<Pawn> pawnSupplier) {
        this.pawnSupplier = Objects.requireNonNull(pawnSupplier, "pawnSupplier must not be null");
    }

    public Pawn buildPawn() {
        return pawnSupplier.get();
    }
}
