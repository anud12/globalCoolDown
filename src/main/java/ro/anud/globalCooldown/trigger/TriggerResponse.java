package ro.anud.globalCooldown.trigger;

import lombok.Builder;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class TriggerResponse {
    private final Optional<Trigger> nextTrigger;

    public Optional<Trigger> getNextTrigger() {
        return nextTrigger;
    }

    @Builder
    public TriggerResponse(final Trigger nextTrigger) {
        this.nextTrigger = ofNullable(nextTrigger);
    }
}
