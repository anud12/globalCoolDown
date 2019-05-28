package ro.anud.globalCooldown.engine.command.planner;

import javafx.geometry.Point2D;
import lombok.*;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.engine.command.CommandScope;

import static ro.anud.globalCooldown.engine.command.planner.CommandPlan.end;
import static ro.anud.globalCooldown.engine.command.planner.CommandPlan.singleInstruction;


@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TeleportCommandPlanner implements CommandPlanner {

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
