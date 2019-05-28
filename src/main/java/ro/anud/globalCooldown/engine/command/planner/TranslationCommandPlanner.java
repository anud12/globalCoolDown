package ro.anud.globalCooldown.engine.command.planner;

import javafx.geometry.Point2D;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.AgilityTrait;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.engine.command.CommandScope;

import java.util.Objects;

import static ro.anud.globalCooldown.engine.command.planner.CommandPlan.end;


@Getter
@EqualsAndHashCode
@ToString
public class TranslationCommandPlanner implements CommandPlanner {
    public static CommandValidator commandValidator = (gameObjectModel, commandScope) ->
            !commandScope.getOptionalValidation()
                    .createChain()
                    .validate(gameObjectModel.getTrait(LocationTrait.class))
                    .validate(gameObjectModel.getTrait(AgilityTrait.class))
                    .isAnyNotPresent();

    private final Point2D destinationLocation;

    public TranslationCommandPlanner(final Point2D destinationLocation) {
        this.destinationLocation = Objects.requireNonNull(destinationLocation, "destinationLocation must not be null");
    }

    private Point2D translate(final GameObjectModel gameObjectModel,
                              final CommandScope commandScope) {
        double deltaTime = commandScope.getProperties().getDeltaTime();
        AgilityTrait agilityTrait = gameObjectModel.getTrait(AgilityTrait.class).get();
        double length = agilityTrait.getTranslationRate() * deltaTime;
        LocationTrait trait = gameObjectModel.getTrait(LocationTrait.class).get();
        Point2D point2D = trait.getPoint2D();

        if (point2D.distance(destinationLocation) <= length) {
            return destinationLocation;
        }

        Point2D locationPoint = new Point2D(point2D.getX(), point2D.getY());
        Point2D vector = locationPoint
                .subtract(destinationLocation)
                .normalize();
        Point2D newLocation = locationPoint
                .subtract(vector.multiply(length));
        return newLocation;
    }

    @Override
    public CommandPlan plan(final GameObjectModel gameObjectModel,
                            final CommandScope commandScope) {
        if (!commandValidator.validate(gameObjectModel, commandScope)) {
            return end();
        }
        LocationTrait trait = gameObjectModel.getTrait(LocationTrait.class).get();
        Point2D newLocation = translate(gameObjectModel, commandScope);
        if (commandScope.getWorldService().isNotBlocked(newLocation)) {
            return end();
        }
        if (newLocation.distance(destinationLocation) == 0) {
            return end();
        }
        return CommandPlan
                .singleInstruction(gameObjectModel,
                        () -> trait.setPoint2D(newLocation))
                .setNextPlanner(this);
    }
}
