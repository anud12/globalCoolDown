package ro.anud.globalcooldown.mapper;

import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.model.ActionModel;

public class ActionOnPawnMapper {
	private ActionOnPawnMapper() {
	}

	public static ActionModel toActionModel(ActionOnPawnEntity actionOnPawnEntity) {
		return ActionModel.builder()
				.id(actionOnPawnEntity.getId())
				.name(actionOnPawnEntity.getName())
				.build();
	}
}
