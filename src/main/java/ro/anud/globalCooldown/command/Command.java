package ro.anud.globalCooldown.command;

public interface Command {
    CommandResponse execute(final CommandArguments commandArguments);
}
