package ro.anud.globalCooldown.command;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import lombok.*;
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
@NoArgsConstructor
public class CreateCommand implements Command {
    @Override
    public CommandResponse execute(final CommandScope commandScope,
                                   final GameObjectModel gameObjectModel) {
        if (commandScope.getOptionalValidation().createChain()
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
                .triggerList(Arrays.asList(commandScope
                                                   .getTriggerBuilder()
                                                   .createGameObjectTrigger(
                                                           Arrays.asList(
                                                                   LocationTrait
                                                                           .builder()
                                                                           .point2D(new Point2D(point.getX(), point.getY()))
                                                                           .modelVertices(Arrays.asList(
                                                                                   new Point2D(-10D, 10D),
                                                                                   new Point2D(10D, 5D),
                                                                                   new Point2D(10D, -5D),
                                                                                   new Point2D(-10D, -10D)
                                                                           ))
                                                                           .angle(0D)
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
                                                                           .color(Color.CYAN)
                                                                           .build()
                                                           ))))
                .nextCommand(null)
                .build();
    }
}
