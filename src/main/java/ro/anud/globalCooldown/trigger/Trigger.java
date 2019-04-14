package ro.anud.globalCooldown.trigger;

public interface Trigger {
    public TriggerResponse execute(final TriggerArguments triggerArguments);
}
