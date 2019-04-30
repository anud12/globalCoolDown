package ro.anud.globalCooldown.trigger;

import java.util.Objects;

public class TriggerDelay implements Trigger {

    private final Long targetDelayTime;
    private final Trigger trigger;
    private Long currentTime = 0L;

    public TriggerDelay(final Long targetDelayTime,
                        final Trigger trigger) {
        this.targetDelayTime = Objects.requireNonNull(targetDelayTime, "targetDelayTime must not be null");
        this.trigger = Objects.requireNonNull(trigger, "trigger must not be null");
    }

    @Override
    public TriggerResponse execute(final TriggerScope triggerScope) {
        currentTime = currentTime + triggerScope.getDeltaTime();
        if (currentTime > targetDelayTime) {
            return trigger.execute(triggerScope);
        }
        return TriggerResponse.builder()
                .nextTrigger(this)
                .build();
    }
}
