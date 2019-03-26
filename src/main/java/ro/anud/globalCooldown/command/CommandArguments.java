package ro.anud.globalCooldown.command;

import lombok.Builder;
import lombok.Getter;
import ro.anud.globalCooldown.model.GameObjectModel;

@Builder
@Getter
public class CommandArguments {
    private GameObjectModel gameObjectModel;
    private Long deltaTime;
}
