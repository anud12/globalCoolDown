package ro.anud.globalcooldown.condition;

import ro.anud.globalcooldown.entity.Pawn;

public interface ConditionOnPawn {
    boolean test(Pawn pawn);
}
