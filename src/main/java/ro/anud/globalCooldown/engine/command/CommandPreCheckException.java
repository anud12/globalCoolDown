package ro.anud.globalCooldown.engine.command;

import lombok.Getter;
import ro.anud.globalCooldown.data.model.GameObjectModel;

import java.util.Objects;

@Getter
public class CommandPreCheckException extends RuntimeException {

    private final GameObjectModel gameObjectModel;
    private final String message;

    public CommandPreCheckException(final GameObjectModel gameObjectModel,
                                    final String message) {
        this.gameObjectModel = Objects.requireNonNull(gameObjectModel, "gameObjectModel must not be null");
        this.message = Objects.requireNonNull(message, "message must not be null");
    }
}
