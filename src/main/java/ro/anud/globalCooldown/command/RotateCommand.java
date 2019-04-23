package ro.anud.globalCooldown.command;

import javafx.geometry.Point2D;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.validation.optionalValidation.OptionalValidation;

import java.util.Objects;

import static java.lang.Math.abs;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class RotateCommand implements Command {

    private final Double targetAngle;

    static Double calculateAngle(Point2D p1, Point2D p2) {
        final double deltaY = (p1.getY() - p2.getY());
        final double deltaX = (p2.getX() - p1.getX());
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return (result < 0) ? (360d + result) : result;
    }

    public RotateCommand(final Double targetAngle) {
        this.targetAngle = Objects.requireNonNull(targetAngle, "targetAngle must not be null");
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
        LocationTrait trait = gameObjectModel.getTrait(LocationTrait.class).get();
        double speed = 0.2;
        double rate = speed * commandScope.getDeltaTime();
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
        if (abs(targetAngle - trait.getAngle()) < rate) {
            trait.setAngle(targetAngle);
            return CommandResponse.builder()
                    .build();
        }
        return CommandResponse.builder()
                .nextCommand(this)
                .build();
    }
}
