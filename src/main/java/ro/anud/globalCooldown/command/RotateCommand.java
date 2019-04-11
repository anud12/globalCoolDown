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
    public CommandResponse execute(final CommandArguments commandArguments) {
        OptionalValidation optionalValidation = commandArguments.getOptionalValidation();
        GameObjectModel gameObjectModel = commandArguments.getGameObjectModel();
        if (optionalValidation.createChain()
                .validate(gameObjectModel.getTrait(LocationTrait.class))
                .isAnyNotPresent()) {
            return CommandResponse.builder()
                    .nextCommand(null)
                    .build();
        }

        LocationTrait trait = gameObjectModel.getTrait(LocationTrait.class).get();
        double speed = 0.1;
        double rate = speed * commandArguments.getDeltaTime();
        double angle = trait.getAngle();

        if (targetAngle > angle) {
            trait.setAngle(angle + rate);
        } else if (targetAngle < angle) {
            trait.setAngle(angle - rate);
        }

        if (Math.abs(targetAngle - trait.getAngle()) > rate) {
            return CommandResponse.builder()
                    .nextCommand(this)
                    .build();
        }
        return CommandResponse.builder()
                .build();
    }
}
