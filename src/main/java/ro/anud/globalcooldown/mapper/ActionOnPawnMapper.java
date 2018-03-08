package ro.anud.globalcooldown.mapper;

import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.model.action.ActionOnPawnOutputModel;

public class ActionOnPawnMapper {
	private ActionOnPawnMapper() {
	}

	public static ActionOnPawnOutputModel toActionModel(ActionOnPawnEntity actionOnPawnEntity) {
		return ActionOnPawnOutputModel.builder()
									  .id(actionOnPawnEntity.getId())
									  .name(actionOnPawnEntity.getName())
									  .build();
	}
}
