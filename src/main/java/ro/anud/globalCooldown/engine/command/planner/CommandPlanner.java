package ro.anud.globalCooldown.engine.command.planner;

import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.command.CommandScope;

public interface CommandPlanner {
    CommandPlan plan(final GameObjectModel gameObjectModel,
                     final CommandScope commandScope);
}
