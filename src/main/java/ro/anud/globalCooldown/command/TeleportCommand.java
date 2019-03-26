package ro.anud.globalCooldown.command;

import javafx.geometry.Point2D;
import lombok.*;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.LocationTrait;

import java.util.Collections;
import java.util.Optional;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeleportCommand implements Command {

    private double x;
    private double y;

    @Override
    public CommandResponse execute(final CommandArguments commandArguments) {
        GameObjectModel gameObjectModel = commandArguments.getGameObjectModel();
        gameObjectModel.getTrait(LocationTrait.class).ifPresent(trait ->
                trait.setPoint2D(new Point2D(x, y))
        );
        return CommandResponse.builder()
                .gameObjectModel(Collections.singletonList(gameObjectModel))
                .command(Optional.empty())
                .build();
    }
}
