package ro.anud.globalCooldown.service;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.trigger.Trigger;
import ro.anud.globalCooldown.trigger.TriggerScope;
import ro.anud.globalCooldown.trigger.TriggerResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TriggerService {

    private final WorldService worldService;
    private final GameObjectService gameObjectService;
    private final TriggerScope triggerScope;

    public TriggerService(final WorldService worldService,
                          final GameObjectService gameObjectService) {
        this.worldService = Objects.requireNonNull(worldService, "worldService must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");

        this.triggerScope = TriggerScope.builder()
                .gameObjectService(gameObjectService)
                .worldService(worldService)
                .build();
    }


    public void processWithNonWorldTriggers(List<Trigger> triggerList) {
        worldService.setTriggerList(worldService.getTriggerList()
                                            .stream()
                                            .map(trigger -> trigger.execute(triggerScope))
                                            .map(TriggerResponse::getNextTrigger)
                                            .filter(Optional::isPresent)
                                            .map(Optional::get)
                                            .collect(Collectors.toList()));

        worldService.getTriggerList()
                .addAll(triggerList.stream()
                                .map(trigger -> trigger.execute(triggerScope))
                                .map(TriggerResponse::getNextTrigger)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList())
                );
    }
}
