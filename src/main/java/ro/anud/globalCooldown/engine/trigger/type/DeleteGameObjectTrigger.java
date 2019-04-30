package ro.anud.globalCooldown.engine.trigger.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.engine.trigger.Trigger;
import ro.anud.globalCooldown.engine.trigger.TriggerResponse;
import ro.anud.globalCooldown.engine.trigger.TriggerScope;

import java.util.Objects;

public class DeleteGameObjectTrigger implements Trigger {

    private final Long id;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteGameObjectTrigger.class);

    public DeleteGameObjectTrigger(final Long id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

    @Override
    public TriggerResponse execute(final TriggerScope triggerScope) {
        LOGGER.info("deleting " + id);
        triggerScope.getGameObjectRepository().deleteById(id);
        return TriggerResponse.builder()
                .nextTrigger(null)
                .build();
    }
}
