package ro.anud.GlobalCooldown.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ro.anud.GlobalCooldown.effects.IncrementValueOnPawn;
import ro.anud.GlobalCooldown.effects.MoveOnPawn;
import ro.anud.GlobalCooldown.entity.EffectOnPawnEntity;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = IncrementValueOnPawnModel.class, name = IncrementValueOnPawn.NAME),
		@JsonSubTypes.Type(value = MoveOnPawnModel.class, name = MoveOnPawn.NAME),
})
public interface EffectOnPawnModel {
	EffectOnPawnEntity toEntity();
}
