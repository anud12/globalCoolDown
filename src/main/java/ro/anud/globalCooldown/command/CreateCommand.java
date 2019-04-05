package ro.anud.globalCooldown.command;

import javafx.geometry.Point2D;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.trait.MetaTrait;

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
        Point2D point = commandArguments.getGameObjectModel()
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
                                new CommandTrait()
                        )
                ))
                .nextCommand(null)
                .build();
    }
}
