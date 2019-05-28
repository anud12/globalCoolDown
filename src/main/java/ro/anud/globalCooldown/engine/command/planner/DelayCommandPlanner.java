package ro.anud.globalCooldown.engine.command.planner;

import lombok.*;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.command.CommandScope;

import java.util.Objects;


@Getter
@EqualsAndHashCode
@ToString
public class DelayCommandPlanner implements CommandPlanner {

    private final Double time;
    private Double passedTime;
    private Double completePercentage;
    private final CommandPlanner next;
    private final CommandValidator validator;

    @Builder
    public DelayCommandPlanner(final Double time,
                               final CommandPlanner next,
                               final CommandValidator validator) {
        this.time = Objects.requireNonNull(time, "time must not be null");
        this.passedTime = 0D;
        this.next = Objects.requireNonNull(next, "next must not be null");
        this.validator = Objects.requireNonNull(validator, "validator must not be null");
    }

    @Override
    public CommandPlan plan(final GameObjectModel gameObjectModel, final CommandScope commandScope) {
        passedTime += commandScope.getProperties().getDeltaTime();
        completePercentage = passedTime / time;
        if (passedTime > time) {
            return CommandPlan.end()
                    .setNextPlanner(this);
        }
        if(!validator.validate(gameObjectModel, commandScope)){
            return CommandPlan.end();
        }
        return next.plan(gameObjectModel, commandScope);
    }
}
