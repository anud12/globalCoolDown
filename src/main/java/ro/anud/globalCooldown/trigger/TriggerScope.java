package ro.anud.globalCooldown.trigger;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.repository.GameObjectRepository;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.service.WorldService;

@Getter
@Builder
public class TriggerScope {
    private GameObjectService gameObjectService;
    private GameObjectRepository gameObjectRepository;
    private WorldService worldService;
    private Long deltaTime;
}
