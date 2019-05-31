package ro.anud.globalCooldown.data.command.impl;

import javafx.geometry.Point2D;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.data.command.Command;
import ro.anud.globalCooldown.data.command.CommandPlan;
import ro.anud.globalCooldown.data.command.CommandScope;
import ro.anud.globalCooldown.data.command.CommandValidator;

import java.util.Objects;

import static ro.anud.globalCooldown.data.command.CommandPlan.end;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MovementCommand implements Command {
    public static CommandValidator commandValidator = (gameObjectModel, commandScope) ->
            RotationCommand.commandValidator.validate(gameObjectModel, commandScope)
                    && TranslationCommand.commandValidator.validate(gameObjectModel, commandScope);

    private static final Logger LOGGER = LoggerFactory.getLogger(MovementCommand.class);
    private final Point2D destinationLocation;
    private final RotationCommand rotationCommand;
    private final TranslationCommand translationCommand;

    public MovementCommand(final Point2D destinationLocation) {
        this.destinationLocation = Objects.requireNonNull(destinationLocation, "destinationLocation must not be null");
        rotationCommand = new RotationCommand(0);
        translationCommand = new TranslationCommand(new Point2D(0, 0));
    }

    @Override
    public CommandPlan plan(final GameObjectModel gameObjectModel,
                            final CommandScope commandScope) {
        if (!commandValidator.validate(gameObjectModel, commandScope)) {
            return end();
        }

        LocationTrait locationTrait = gameObjectModel.getTrait(LocationTrait.class).get();

        if (locationTrait.getPoint2D().distance(destinationLocation) == 0) {
            return end();
        }
        if (isMissaligned(locationTrait, commandScope.getProperties().getEpsilon())) {
            rotationCommand.setTargetAngle(getDestinationAlignment(locationTrait));
            return rotationCommand
                    .plan(gameObjectModel, commandScope)
                    .setNextPlanner(this);
        }
        translationCommand.setDestinationLocation(destinationLocation);
        return translationCommand
                .plan(gameObjectModel, commandScope)
                .setNextPlanner(this);
    }

    private Double getDestinationAlignment(LocationTrait trait) {
        return RotationCommand.calculateAngle(trait.getPoint2D(), destinationLocation);
    }

    private boolean isMissaligned(LocationTrait locationTrait, Double epsilon) {
        return !(Math.abs(locationTrait.getAngle() - getDestinationAlignment(locationTrait)) <= epsilon);
    }
}
