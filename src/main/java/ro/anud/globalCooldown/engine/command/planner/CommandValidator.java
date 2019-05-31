package ro.anud.globalCooldown.engine.command.planner;

import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.command.CommandScope;

public interface CommandValidator {
    boolean validate(final GameObjectModel gameObjectModel,
                                               final CommandScope commandScope);
}
