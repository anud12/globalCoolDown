package ro.anud.globalCooldown.engine.command.planner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.command.CommandScope;

import java.util.Objects;


@Getter
@EqualsAndHashCode
@ToString
public class DelayCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelayCommand.class);
    private final Double time;
    private Double passedTime;
    private Double completePercentage;
    private final Command next;

    @JsonIgnore
    private final CommandValidator validator;

    @Builder
    public DelayCommand(final Double time,
                        final Command next,
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
        if (time > passedTime) {
            return CommandPlan.end()
                    .setNextPlanner(this);
        }
        if(!validator.validate(gameObjectModel, commandScope)){
            LOGGER.info("invalid");
            gameObjectModel.getTraitMap().forEach((s, trait) -> LOGGER.info(s + " " + trait));
            return CommandPlan.end();
        }
        return next.plan(gameObjectModel, commandScope);
    }
}
