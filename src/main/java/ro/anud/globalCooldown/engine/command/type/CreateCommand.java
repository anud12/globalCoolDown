package ro.anud.globalCooldown.engine.command.type;

import javafx.geometry.Point2D;
import lombok.*;
import ro.anud.globalCooldown.engine.command.Command;
import ro.anud.globalCooldown.engine.command.CommandResponse;
import ro.anud.globalCooldown.engine.command.CommandScope;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.model.RGBA;
import ro.anud.globalCooldown.data.trait.*;

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
                                                   .getTriggerFactory()
                                                   .createGameObjectTrigger(
                                                           Arrays.asList(
                                                                   LocationTrait
                                                                           .builder()
                                                                           .point2D(new Point2D(point.getX(), point.getY()))
                                                                           .angle(0D)
                                                                           .build(),
                                                                   ModelTrait.builder()
                                                                           .vertexPointList(Arrays.asList(
                                                                                   new Point2D(-10D, 10D),
                                                                                   new Point2D(10D, 5D),
                                                                                   new Point2D(10D, -5D),
                                                                                   new Point2D(-10D, -10D)
                                                                           ))
                                                                           .angleOffset(0D)
                                                                           .vertexColor(RGBA.builder()
                                                                                                .red(0D)
                                                                                                .green(0D)
                                                                                                .blue(0D)
                                                                                                .alpha(1D)
                                                                                                .build()
                                                                           )
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
                                                                           .vertexColor(RGBA.builder()
                                                                                                .red(0D)
                                                                                                .green(0D)
                                                                                                .blue(0D)
                                                                                                .alpha(1D)
                                                                                                .build()
                                                                           )
                                                                           .build()
                                                           ))))
                .nextCommand(null)
                .build();
    }
}
