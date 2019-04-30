package ro.anud.globalCooldown.engine.factory;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.engine.command.type.CreateCommand;
import ro.anud.globalCooldown.engine.command.type.MoveCommand;
import ro.anud.globalCooldown.engine.command.type.RotateCommand;

@Service
public class CommandFactory {
    public MoveCommand moveCommand(Point2D destinationLocation) {
        return new MoveCommand(destinationLocation);
    }

    public RotateCommand rotateCommand(Double targetAngle) {
        return new RotateCommand(targetAngle);
    }

    public CreateCommand createCommand() {
        return new CreateCommand();
    }
}
