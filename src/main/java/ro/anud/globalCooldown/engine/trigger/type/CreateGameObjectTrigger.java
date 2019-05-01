package ro.anud.globalCooldown.engine.trigger.type;

import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.trigger.Trigger;
import ro.anud.globalCooldown.engine.trigger.TriggerResponse;
import ro.anud.globalCooldown.engine.trigger.TriggerScope;

import java.util.Objects;

public class CreateGameObjectTrigger implements Trigger {

    private final GameObjectModel gameObjectModel;

    public CreateGameObjectTrigger(final GameObjectModel gameObjectModel) {
        this.gameObjectModel = Objects.requireNonNull(gameObjectModel, "gameObjectModel must not be null");
    }

    @Override
    public TriggerResponse execute(final TriggerScope triggerScope) {
        triggerScope.getGameObjectRepository().insert(gameObjectModel);
        return TriggerResponse.builder()
                .nextTrigger(null)
                .build();
    }
}
