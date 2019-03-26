package ro.anud.globalCooldown.command;

import javafx.geometry.Point2D;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.LocationTrait;

import java.util.Collections;
import java.util.Optional;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class MoveCommand implements Command {

    private final Point2D destinationLocation;

    public MoveCommand(Point2D destinationLocation) {
        this.destinationLocation = destinationLocation;
    }


    @Override
    public CommandResponse execute(final CommandArguments commandArguments) {
        GameObjectModel gameObjectModel = commandArguments.getGameObjectModel();
        int speed = 5;
        return gameObjectModel.getTrait(LocationTrait.class)
                .map(trait -> {
                    Point2D point2D = trait.getPoint2D();

                    Optional<Command> nextCommand = Optional.of(this);
                    Point2D locationPoint = new Point2D(point2D.getX(), point2D.getY());
                    Point2D traslation = locationPoint.subtract(destinationLocation);
                    traslation = traslation.normalize();
                    traslation = traslation.multiply(speed / commandArguments.getDeltaTime().floatValue());
                    Point2D newLocation = locationPoint.subtract(traslation);
                    System.out.println(newLocation);
                    trait.setPoint2D(newLocation);
                    if (point2D.distance(destinationLocation) < speed) {
                        nextCommand = Optional.empty();
                    }

                    return CommandResponse.builder()
                            .command(nextCommand)
                            .gameObjectModel(Collections.singletonList(commandArguments.getGameObjectModel()))
                            .build();
                })
                .orElse(CommandResponse.builder()
                        .command(Optional.empty())
                        .gameObjectModel(Collections.singletonList(commandArguments.getGameObjectModel()))
                        .build());

    }
}
