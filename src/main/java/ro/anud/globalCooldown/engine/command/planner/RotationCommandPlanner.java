package ro.anud.globalCooldown.engine.command.planner;

import javafx.geometry.Point2D;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.AgilityTrait;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.engine.command.CommandScope;
import ro.anud.globalCooldown.engine.command.type.RotateCommand;

import java.util.Objects;

import static java.lang.Math.abs;
import static ro.anud.globalCooldown.engine.command.planner.CommandPlan.end;
import static ro.anud.globalCooldown.engine.command.planner.CommandPlan.singleInstruction;


@Getter
@EqualsAndHashCode
@ToString
public class RotationCommandPlanner implements CommandPlanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RotateCommand.class);
    public static CommandValidator commandValidator = (gameObjectModel, commandScope) ->
            !commandScope.getOptionalValidation()
                    .createChain()
                    .validate(gameObjectModel.getTrait(LocationTrait.class))
                    .validate(gameObjectModel.getTrait(AgilityTrait.class))
                    .isAnyNotPresent();

    private final double targetAngle;

    static Double calculateAngle(Point2D p1, Point2D p2) {
        final double deltaY = (p1.getY() - p2.getY());
        final double deltaX = (p2.getX() - p1.getX());
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return (result < 0) ? (360d + result) : result;
    }

    public RotationCommandPlanner(final double targetAngle) {
        this.targetAngle = Objects.requireNonNull(targetAngle, "targetAngle must not be null");
    }

    private Double rotate(final LocationTrait trait,
                          final double rate) {
        double angle = trait.getAngle();

        double newAngle;
        if (angle < targetAngle) {
            if (abs(angle - targetAngle) < 180) {
                newAngle = angle + rate;
            } else {
                newAngle = angle - rate;
            }
        } else {
            if (abs(angle - targetAngle) < 180) {
                newAngle = angle - rate;
            } else {
                newAngle = angle + rate;
            }
        }
        if (newAngle < 0) {
            newAngle += 360;
        }
        if (newAngle > 360) {
            newAngle -= 360;
        }

        if (abs(targetAngle - newAngle) <= rate) {
            newAngle = targetAngle;
        }
        return newAngle;
    }

    @Override
    public CommandPlan plan(final GameObjectModel gameObjectModel,
                                        final CommandScope commandScope) {
        if (!commandValidator.validate(gameObjectModel, commandScope)) {
            return end();
        }
        LocationTrait trait = gameObjectModel.getTrait(LocationTrait.class).get();
        AgilityTrait agilityTrait = gameObjectModel.getTrait(AgilityTrait.class).get();
        double rate = agilityTrait.getRotationRate() * commandScope.getProperties().getDeltaTime();

        final double finalNewAngle = rotate(trait, rate);
        if (finalNewAngle == trait.getAngle()) {
            return end();
        }
        return singleInstruction(gameObjectModel,
                () -> trait.setAngle(finalNewAngle))
                .setNextPlanner(this);
    }
}
