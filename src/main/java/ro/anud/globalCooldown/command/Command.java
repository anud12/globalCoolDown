package ro.anud.globalCooldown.command;

import ro.anud.globalCooldown.model.GameObjectModel;

public interface Command {
    CommandResponse execute(final CommandScope commandScope,
                            final GameObjectModel gameObjectModel);
}
