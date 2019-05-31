package ro.anud.globalCooldown.data.command.impl;

import javafx.geometry.Point2D;
import lombok.*;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.data.command.Command;
import ro.anud.globalCooldown.data.command.CommandPlan;
import ro.anud.globalCooldown.data.command.CommandScope;
import ro.anud.globalCooldown.data.command.CommandValidator;

import static ro.anud.globalCooldown.data.command.CommandPlan.end;
import static ro.anud.globalCooldown.data.command.CommandPlan.singleInstruction;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TeleportCommand implements Command {

    public static CommandValidator commandValidator = (gameObjectModel, commandScope) ->
            commandScope.getOptionalValidation()
                    .createChain().validate(gameObjectModel.getTrait(LocationTrait.class))
                    .isAnyNotPresent();

    private double x;
    private double y;

    @Override
    public CommandPlan plan(final GameObjectModel gameObjectModel,
                            final CommandScope commandScope) {
        if(!commandValidator.validate(gameObjectModel, commandScope)){
            return end();
        }
        return singleInstruction(gameObjectModel,
                () -> gameObjectModel.getTrait(LocationTrait.class).get().setPoint2D(new Point2D(x, y))
        );
    }
}
