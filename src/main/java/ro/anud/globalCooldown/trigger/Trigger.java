package ro.anud.globalCooldown.trigger;

public interface Trigger {
    TriggerResponse execute(final TriggerScope triggerScope);
}
