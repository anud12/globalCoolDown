package ro.anud.globalCooldown.engine.trigger.type;

import ro.anud.globalCooldown.engine.trigger.Trigger;
import ro.anud.globalCooldown.engine.trigger.TriggerResponse;
import ro.anud.globalCooldown.engine.trigger.TriggerScope;
import ro.anud.globalCooldown.data.trait.Trait;

import java.util.List;
import java.util.Objects;

public class CreateGameObjectTrigger implements Trigger {

    private final List<Trait> traitList;

    public CreateGameObjectTrigger(List<Trait> traitList) {
        this.traitList = Objects.requireNonNull(traitList, "traitList must not be null");
    }

    @Override
    public TriggerResponse execute(final TriggerScope triggerScope) {
        triggerScope.getGameObjectRepository().insert(triggerScope.getGameObjectFactory().createFromTraits(traitList));
        return TriggerResponse.builder()
                .nextTrigger(null)
                .build();
    }
}
