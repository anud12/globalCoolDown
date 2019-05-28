package ro.anud.globalCooldown.engine.command.planner;

import javafx.geometry.Point2D;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.AgilityTrait;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.engine.command.CommandScope;

import java.util.Objects;

import static ro.anud.globalCooldown.engine.command.planner.CommandPlan.end;


@Getter
@EqualsAndHashCode
@ToString
public class MovementCommandPlanner implements CommandPlanner {
    public static CommandValidator commandValidator = (gameObjectModel, commandScope) ->
            RotationCommandPlanner.commandValidator.validate(gameObjectModel, commandScope)
                    && TranslationCommandPlanner.commandValidator.validate(gameObjectModel, commandScope);

    private static final Logger LOGGER = LoggerFactory.getLogger(MovementCommandPlanner.class);
    private final Point2D destinationLocation;

    public MovementCommandPlanner(final Point2D destinationLocation) {
        this.destinationLocation = Objects.requireNonNull(destinationLocation, "destinationLocation must not be null");
    }

    @Override
    public CommandPlan plan(final GameObjectModel gameObjectModel,
                                        final CommandScope commandScope) {
        if (!commandValidator.validate(gameObjectModel, commandScope)) {
            return end();
        }

        LocationTrait locationTrait = gameObjectModel.getTrait(LocationTrait.class).get();

        if(locationTrait.getPoint2D().distance(destinationLocation) == 0){
            return end();
        }
        if (isMissaligned(locationTrait, commandScope.getProperties().getEpsilon())) {
            return new RotationCommandPlanner(getDestinationAlignment(locationTrait))
                    .plan(gameObjectModel, commandScope)
                    .setNextPlanner(this);
        }
        return new TranslationCommandPlanner(destinationLocation)
                .plan(gameObjectModel, commandScope)
                .setNextPlanner(this);
    }

    private Double getDestinationAlignment(LocationTrait trait) {
        return RotationCommandPlanner.calculateAngle(trait.getPoint2D(), destinationLocation);
    }

    private boolean isMissaligned(LocationTrait locationTrait, Double epsilon) {
        return !(Math.abs(locationTrait.getAngle() - getDestinationAlignment(locationTrait)) <= epsilon);
    }
}
