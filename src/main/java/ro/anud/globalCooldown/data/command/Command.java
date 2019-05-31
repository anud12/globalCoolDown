package ro.anud.globalCooldown.data.command;

import ro.anud.globalCooldown.data.model.GameObjectModel;

public interface Command {
    CommandPlan plan(final GameObjectModel gameObjectModel,
                     final CommandScope commandScope);
}
