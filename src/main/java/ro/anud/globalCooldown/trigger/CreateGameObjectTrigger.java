package ro.anud.globalCooldown.trigger;

import ro.anud.globalCooldown.trait.Trait;

import java.util.List;
import java.util.Objects;

public class CreateGameObjectTrigger implements Trigger {

    private final List<Trait> traitList;

    public CreateGameObjectTrigger(List<Trait> traitList) {
        this.traitList = Objects.requireNonNull(traitList, "traitList must not be null");
    }

    @Override
    public TriggerResponse execute(final TriggerArguments triggerArguments) {
        triggerArguments.getGameObjectService().create(traitList);
        return TriggerResponse.builder()
                .nextTrigger(null)
                .build();
    }
}
