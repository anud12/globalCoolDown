package ro.anud.globalcooldown.effects;

import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;

public interface EffectOnPawn {
    Pawn execute();

    boolean isRemovable();

    boolean isExecutable();

    EffectOnPawnPriority getPriority();

    Long getId();

    EffectOnPawnEntity toEntity();

    void incrementAge();

    void resetAge();

    Integer getAge();
}
