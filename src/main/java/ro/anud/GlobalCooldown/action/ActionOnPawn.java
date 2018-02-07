package ro.anud.GlobalCooldown.action;

import ro.anud.GlobalCooldown.entity.ActionOnPawnEntity;
import ro.anud.GlobalCooldown.entity.Pawn;
public interface ActionOnPawn {
	Pawn execute();
	boolean isArrived();
	ActionOnPawnEntity toEntity();
}
