package ro.anud.globalCooldown.engine.command;

import ro.anud.globalCooldown.data.model.GameObjectModel;

public interface CommandValidator {
    boolean validate(GameObjectModel gameObjectModel);
}
