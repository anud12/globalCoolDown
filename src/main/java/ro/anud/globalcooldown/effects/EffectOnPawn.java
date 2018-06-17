package ro.anud.globalcooldown.effects;

import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.service.GameDataService;

public interface EffectOnPawn {

    public abstract Pawn execute(GameDataService gameDataService);

    public abstract boolean isRemovable();

    public abstract EffectOnPawnPriority getPriority();

    public abstract EffectOnPawnEntity toEntity();

    public abstract boolean isExecutable();

    public abstract void incrementAge();

    public abstract void resetAge();

    public abstract Integer getAge();
}
