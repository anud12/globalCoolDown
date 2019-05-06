package ro.anud.globalCooldown.engine.factory;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.command.type.CreateCommand;
import ro.anud.globalCooldown.engine.command.type.MoveCommand;
import ro.anud.globalCooldown.engine.command.type.RotateCommand;

@Service
public class CommandFactory {
    public MoveCommand moveCommand(final GameObjectModel targetGameObjectModel,
                                   final Point2D destinationLocation) {
        return new MoveCommand(targetGameObjectModel, destinationLocation);
    }

    public RotateCommand rotateCommand(final GameObjectModel targetGameObjectModel,
                                       final Double targetAngle) {
        return new RotateCommand(targetGameObjectModel, targetAngle);
    }

    public CreateCommand createCommand(final GameObjectModel targetGameObjectModel,
                                       final GameObjectModel gameObjectModel) {
        return new CreateCommand(targetGameObjectModel, gameObjectModel);
    }
}
