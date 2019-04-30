package ro.anud.globalCooldown.engine.command;

import ro.anud.globalCooldown.data.model.GameObjectModel;

public interface Command {
    CommandResponse execute(final CommandScope commandScope,
                            final GameObjectModel gameObjectModel);
}
