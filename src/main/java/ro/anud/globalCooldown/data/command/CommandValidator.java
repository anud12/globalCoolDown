package ro.anud.globalCooldown.data.command;

import ro.anud.globalCooldown.data.model.GameObjectModel;

public interface CommandValidator {
    boolean validate(final GameObjectModel gameObjectModel,
                     final CommandScope commandScope);
}
