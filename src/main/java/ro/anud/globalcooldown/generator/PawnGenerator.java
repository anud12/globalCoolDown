package ro.anud.globalcooldown.generator;

import ro.anud.globalcooldown.effects.IncrementValueOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.entity.effect.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.effect.IncrementValueOnPawnEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public enum PawnGenerator {
    PAWN(() -> Pawn.builder()
            .name("Pawn")
            .value(0L)
            .characterCode(80L)
            .speed(100L)
            .version(0L)
            .build(),
         (pawn, actionOnPawnEntity) -> {
             Set<EffectOnPawnEntity> list = new HashSet<>();
             list.add(IncrementValueOnPawnEntity.builder()
                              .duration(100000)
                              .rate(1)
                              .age(0)
                              .isSideEffect(false)
                              .type(IncrementValueOnPawn.NAME)
                              .pawn(pawn)
                              .action(actionOnPawnEntity)
                              .build());
             return list;
         });


    private Supplier<Pawn> pawnSupplier;
    private BiFunction<Pawn, ActionOnPawnEntity, Set<EffectOnPawnEntity>> pawnEffectListFunction;

    PawnGenerator(final Supplier<Pawn> pawnSupplier,
                  BiFunction<Pawn, ActionOnPawnEntity, Set<EffectOnPawnEntity>> pawnEffectListFunction) {
        this.pawnSupplier = Objects.requireNonNull(pawnSupplier, "pawnSupplier must not be null");
        this.pawnEffectListFunction = Objects.requireNonNull(pawnEffectListFunction,
                                                             "pawnEffectListFunction must not be null");
    }

    public Pawn createPawn() {
        return pawnSupplier.get();
    }

    public Set<EffectOnPawnEntity> createEffectsForPawn(Pawn pawn, ActionOnPawnEntity actionOnPawnEntity) {
        return pawnEffectListFunction.apply(pawn, actionOnPawnEntity);
    }
}
