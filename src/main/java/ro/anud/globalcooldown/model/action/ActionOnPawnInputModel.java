package ro.anud.globalcooldown.model.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IncrementValueOnPawnActionInputModel.class, name = IncrementValueOnPawnActionInputModel.NAME),
        @JsonSubTypes.Type(value = MoveOnPawnActionInputModel.class, name = MoveOnPawnActionInputModel.NAME),
})
public interface ActionOnPawnInputModel {
    ActionOnPawnEntity toEntity();
}
