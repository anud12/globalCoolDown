package ro.anud.globalCooldown.trigger;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.service.WorldService;

@Getter
@Builder
public class TriggerArguments {
    private GameObjectService gameObjectService;
    private WorldService worldService;
}
