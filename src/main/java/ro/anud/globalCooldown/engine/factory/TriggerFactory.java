package ro.anud.globalCooldown.engine.factory;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.trigger.type.CreateGameObjectTrigger;

@Service
public class TriggerFactory {
    public CreateGameObjectTrigger createGameObjectTrigger(GameObjectModel gameObjectModel) {
        return new CreateGameObjectTrigger(gameObjectModel);
    }
}
