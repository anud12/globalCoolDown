package ro.anud.GlobalCooldown.effects;

import ro.anud.GlobalCooldown.entity.EffectOnPawnEntity;
import ro.anud.GlobalCooldown.entity.Pawn;
public interface EffectOnPawn {
	Pawn execute();
	boolean isArrived();
	EffectOnPawnEntity toEntity();
}
