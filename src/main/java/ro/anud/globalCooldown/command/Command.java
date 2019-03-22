package ro.anud.globalCooldown.command;

import ro.anud.globalCooldown.model.GameObjectModel;

public interface Command {
    public CommandResponse execute(final GameObjectModel gameObjectModel);
}
