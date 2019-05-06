package ro.anud.globalCooldown.engine.command.type;

import javafx.geometry.Point2D;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.api.validation.optionalValidation.OptionalValidation;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.AgilityTrait;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.engine.command.Command;
import ro.anud.globalCooldown.engine.command.CommandPreCheckException;
import ro.anud.globalCooldown.engine.command.CommandResponse;
import ro.anud.globalCooldown.engine.command.CommandScope;

import java.util.Objects;

import static java.lang.Math.abs;

@SuppressWarnings("Duplicates")
@Getter
@ToString
@EqualsAndHashCode
public class RotateCommand implements Command {

    private final Double targetAngle;
    private static final Logger LOGGER = LoggerFactory.getLogger(RotateCommand.class);

    static Double calculateAngle(Point2D p1, Point2D p2) {
        final double deltaY = (p1.getY() - p2.getY());
        final double deltaX = (p2.getX() - p1.getX());
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return (result < 0) ? (360d + result) : result;
    }

    @Builder
    public RotateCommand(final GameObjectModel targetGameObjectModel,
                         final Double targetAngle) {
        this.targetAngle = Objects.requireNonNull(targetAngle, "targetAngle must not be null");
        if (!targetGameObjectModel.getTrait(LocationTrait.class).isPresent()
                || !targetGameObjectModel.getTrait(AgilityTrait.class).isPresent()) {
            throw new CommandPreCheckException(targetGameObjectModel, "Can't rotate!");
        }
    }

    @Override
    public CommandResponse execute(final CommandScope commandScope,
                                   final GameObjectModel gameObjectModel) {
        OptionalValidation optionalValidation = commandScope.getOptionalValidation();
        if (optionalValidation.createChain()
                .validate(gameObjectModel.getTrait(LocationTrait.class))
                .validate(gameObjectModel.getTrait(AgilityTrait.class))
                .isAnyNotPresent()) {
            return CommandResponse.builder()
                    .nextCommand(null)
                    .build();
        }
        LocationTrait trait = gameObjectModel.getTrait(LocationTrait.class).get();
        AgilityTrait agilityTrait = gameObjectModel.getTrait(AgilityTrait.class).get();
        double rate = agilityTrait.getRotationRate() * commandScope.getProperties().getDeltaTime();
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
        trait.setAngle(newAngle);
        if (abs(targetAngle - trait.getAngle()) <= rate) {
            trait.setAngle(targetAngle);
            return CommandResponse.builder()
                    .build();
        }
        return CommandResponse.builder()
                .nextCommand(this)
                .build();
    }
}
