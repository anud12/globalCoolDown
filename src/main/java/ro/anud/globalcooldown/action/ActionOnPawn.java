package ro.anud.globalcooldown.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = IncrementValueOnPawnAction.class, name = IncrementValueOnPawnAction.NAME),
		@JsonSubTypes.Type(value = MoveOnPawnAction.class, name = MoveOnPawnAction.NAME),
})
public interface ActionOnPawn {
	ActionOnPawnEntity toEntity();
}
