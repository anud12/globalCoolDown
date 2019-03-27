package ro.anud.globalCooldown.command;

import javafx.geometry.Point2D;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.validation.OptionalValidation;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class MoveCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveCommand.class);
    private final Point2D destinationLocation;

    public MoveCommand(final Point2D destinationLocation) {
        this.destinationLocation = destinationLocation;
    }


    @Override
    public CommandResponse execute(final CommandArguments commandArguments) {
        OptionalValidation optionalValidation = commandArguments.getOptionalValidation();
        GameObjectModel gameObjectModel = commandArguments.getGameObjectModel();
        if (optionalValidation.createChain()
                .validate(gameObjectModel.getTrait(LocationTrait.class))
                .isAnyNotPresent()) {
            return CommandResponse.builder()
                    .command(null)
                    .build();
        }
        double speed = 0.01;
        double length = speed * commandArguments.getDeltaTime().floatValue();
        LocationTrait trait = gameObjectModel.getTrait(LocationTrait.class).get();
        Point2D point2D = trait.getPoint2D();
        Point2D locationPoint = new Point2D(point2D.getX(), point2D.getY());
        Point2D newLocation = locationPoint
                .subtract(locationPoint
                        .subtract(destinationLocation)
                        .normalize()
                        .multiply(length)
                );
        LOGGER.info(newLocation.toString());
        trait.setPoint2D(newLocation);
        if (point2D.distance(destinationLocation) <= length) {
            return CommandResponse.builder()
                    .command(null)
                    .build();
        }

        return CommandResponse.builder()
                .command(this)
                .build();
    }
}
