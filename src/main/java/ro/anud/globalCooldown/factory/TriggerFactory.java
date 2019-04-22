package ro.anud.globalCooldown.factory;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.trait.Trait;
import ro.anud.globalCooldown.trigger.CreateGameObjectTrigger;

import java.util.List;

@Service
public class TriggerFactory {
    public CreateGameObjectTrigger createGameObjectTrigger(List<Trait> traitList) {
        return new CreateGameObjectTrigger(traitList);
    }
}
