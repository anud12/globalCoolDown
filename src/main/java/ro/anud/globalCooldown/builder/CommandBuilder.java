package ro.anud.globalCooldown.builder;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.command.CreateCommand;
import ro.anud.globalCooldown.command.MoveCommand;
import ro.anud.globalCooldown.command.RotateCommand;

@Service
public class CommandBuilder {
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
