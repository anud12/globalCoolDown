package ro.anud.globalCooldown.engine.command.planner;

import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.command.CommandScope;

public interface Command {
    CommandPlan plan(final GameObjectModel gameObjectModel,
                     final CommandScope commandScope);
}
