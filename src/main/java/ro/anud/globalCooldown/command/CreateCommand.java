package ro.anud.globalCooldown.command;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.trait.OwnerTrait;
import ro.anud.globalCooldown.trait.RenderTrait;

import java.util.Arrays;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class CreateCommand implements Command {
    @Override
    public CommandResponse execute(final CommandArguments commandArguments) {
        GameObjectModel gameObjectModel = commandArguments.getGameObjectModel();
        if (commandArguments.getOptionalValidation().createChain()
                .validate(gameObjectModel.getTrait(LocationTrait.class))
                .isAnyNotPresent()) {
            return CommandResponse.builder()
                    .nextCommand(null)
                    .build();
        }
        Point2D point = gameObjectModel
                .getTrait(LocationTrait.class)
                .get()
                .getPoint2D();

        return CommandResponse.builder()
                .createdGameObjectModelTrait(Arrays.asList(
                        Arrays.asList(
                                LocationTrait
                                        .builder()
                                        .point2D(new Point2D(point.getX(), point.getY()))
                                        .build(),
                                new CommandTrait(),
                                OwnerTrait.builder()
                                        .ownerId(gameObjectModel.getTrait(OwnerTrait.class)
                                                         .map(OwnerTrait::getOwnerId)
                                                         .orElse(""))
                                        .build(),
                                RenderTrait.builder()
                                        .modelPointList(Arrays.asList(
                                                new Point2D(-10D, -10D),
                                                new Point2D(10D, -10D),
                                                new Point2D(10D, 10D)
                                        ))
                                        .color(Color.GREEN)
                                        .build()
                        )
                ))
                .nextCommand(null)
                .build();
    }
}
