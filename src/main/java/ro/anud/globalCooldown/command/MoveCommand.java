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
import ro.anud.globalCooldown.validation.optionalValidation.OptionalValidation;

import static java.util.Optional.of;
import static ro.anud.globalCooldown.command.RotateCommand.calculateAngle;

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
    public CommandResponse execute(final CommandScope commandScope,
                                   final GameObjectModel gameObjectModel) {
        OptionalValidation optionalValidation = commandScope.getOptionalValidation();
        if (optionalValidation.createChain()
                .validate(gameObjectModel.getTrait(LocationTrait.class))
                .isAnyNotPresent()) {
            return CommandResponse.builder()
                    .nextCommand(null)
                    .build();
        }
        double speed = 0.1;
        double length = speed * commandScope.getDeltaTime().floatValue();
        LocationTrait trait = gameObjectModel.getTrait(LocationTrait.class).get();

        if (isMissaligned(gameObjectModel, commandScope.getDeltaTime())) {
            return commandScope
                    .getCommandBuilder()
                    .rotateCommand(getDestinationAlignment(trait))
                    .execute(commandScope, gameObjectModel)
                    .setNextCommand(of(this));
        }
        Point2D point2D = trait.getPoint2D();
        Point2D locationPoint = new Point2D(point2D.getX(), point2D.getY());
        Point2D vector = locationPoint
                .subtract(destinationLocation)
                .normalize();
        Point2D newLocation = locationPoint
                .subtract(vector.multiply(length));

        if (commandScope.getWorldService().isNotBlocked(newLocation)) {
            return CommandResponse.builder()
                    .nextCommand(null)
                    .build();
        }

        if (point2D.distance(destinationLocation) <= length) {
            trait.setPoint2D(destinationLocation);
            return CommandResponse.builder()
                    .nextCommand(null)
                    .build();
        }
        trait.setPoint2D(newLocation);
        return CommandResponse.builder()
                .nextCommand(this)
                .build();
    }

    private Double getDestinationAlignment(LocationTrait trait) {
        return calculateAngle(trait.getPoint2D(), destinationLocation);
    }

    private boolean isMissaligned(GameObjectModel gameObjectModel, Long deltaTime) {
        double rate = 0.1 * deltaTime;
        LocationTrait locationTrait = gameObjectModel.getTrait(LocationTrait.class).get();
        Boolean result = !(Math.abs(locationTrait.getAngle() - getDestinationAlignment(locationTrait)) < rate);
        return result;
    }
}
