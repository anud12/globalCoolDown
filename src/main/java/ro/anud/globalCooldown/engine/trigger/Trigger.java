package ro.anud.globalCooldown.engine.trigger;

public interface Trigger {
    TriggerResponse execute(final TriggerScope triggerScope);
}
