package ro.anud.globalCooldown.engine.trigger;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.data.factory.GameObjectFactory;
import ro.anud.globalCooldown.data.repository.GameObjectRepository;
import ro.anud.globalCooldown.data.service.GameObjectService;
import ro.anud.globalCooldown.data.service.WorldService;

@Getter
@Builder
public class TriggerScope {
    private GameObjectService gameObjectService;
    private GameObjectFactory gameObjectFactory;
    private GameObjectRepository gameObjectRepository;
    private WorldService worldService;
    private Long deltaTime;
}
