package ro.anud.globalCooldown.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.model.GameObjectModel;

import java.util.List;
import java.util.Optional;

@Getter
@Builder
@ToString
public class CommandResponse {
    public List<GameObjectModel> gameObjectModel;
    public Optional<Command> command;
}
