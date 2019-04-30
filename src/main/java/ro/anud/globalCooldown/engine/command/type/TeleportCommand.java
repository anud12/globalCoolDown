package ro.anud.globalCooldown.engine.command.type;

import javafx.geometry.Point2D;
import lombok.*;
import ro.anud.globalCooldown.engine.command.Command;
import ro.anud.globalCooldown.engine.command.CommandResponse;
import ro.anud.globalCooldown.engine.command.CommandScope;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.LocationTrait;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeleportCommand implements Command {

    private double x;
    private double y;

    @Override
    public CommandResponse execute(final CommandScope commandScope,
                                   final GameObjectModel gameObjectModel) {
        gameObjectModel.getTrait(LocationTrait.class)
                .ifPresent(trait ->
                                   trait.setPoint2D(new Point2D(x, y))
                );
        return CommandResponse.builder()
                .nextCommand(null)
                .build();
    }
}
