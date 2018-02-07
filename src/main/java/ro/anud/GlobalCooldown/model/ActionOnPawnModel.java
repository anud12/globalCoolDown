package ro.anud.GlobalCooldown.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ro.anud.GlobalCooldown.action.IncrementValueOnPawn;
import ro.anud.GlobalCooldown.action.MoveOnPawn;
import ro.anud.GlobalCooldown.entity.ActionOnPawnEntity;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = IncrementValueOnPawnModel.class, name = IncrementValueOnPawn.NAME),
		@JsonSubTypes.Type(value = MoveOnPawnModel.class, name = MoveOnPawn.NAME),
})
public interface ActionOnPawnModel {
	ActionOnPawnEntity toEntity();
}
