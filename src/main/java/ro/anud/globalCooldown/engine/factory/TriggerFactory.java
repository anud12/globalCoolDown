package ro.anud.globalCooldown.engine.factory;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.data.trait.Trait;
import ro.anud.globalCooldown.engine.trigger.type.CreateGameObjectTrigger;

import java.util.List;

@Service
public class TriggerFactory {
    public CreateGameObjectTrigger createGameObjectTrigger(List<Trait> traitList) {
        return new CreateGameObjectTrigger(traitList);
    }
}
