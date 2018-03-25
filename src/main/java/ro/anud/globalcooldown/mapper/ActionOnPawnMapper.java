package ro.anud.globalcooldown.mapper;

import ro.anud.globalcooldown.action.ActionOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;

import java.util.Objects;

public class ActionOnPawnMapper {
    private ActionOnPawnMapper() {
    }

    public static ActionOnPawn toAction(ActionOnPawnEntity actionOnPawnEntity) {
        if (Objects.isNull(actionOnPawnEntity)) {
            return null;
        }
        return ActionOnPawn.builder()
                .id(actionOnPawnEntity.getId())
                .depth(extractDepth(actionOnPawnEntity))
                .type(actionOnPawnEntity.getName())
                .build();
    }


    private static Integer extractDepth(ActionOnPawnEntity actionOnPawnEntity) {
        int depth = -1;
        ActionOnPawnEntity action = actionOnPawnEntity;
        do {
            depth++;
            action = action.getParent();
        } while (Objects.nonNull(action));
        return depth;
    }
}
