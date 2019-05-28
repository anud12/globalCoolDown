package ro.anud.globalCooldown.engine.command.planner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ro.anud.globalCooldown.data.model.GameObjectModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.*;
import static java.util.Optional.empty;

@Getter
@AllArgsConstructor
public class CommandPlan {
    public static CommandPlan end(){
        return new CommandPlan(empty(), emptyMap());
    }

    public static CommandPlan untargetedInstruction(final Runnable instruction){
        return new CommandPlan(empty(), singletonMap(null, singletonList(instruction)));
    }
    public static CommandPlan singleInstruction(final GameObjectModel target,
                                         final Runnable instruction){
        return new CommandPlan(empty(), singletonMap(target, singletonList(instruction)));
    }

    private Optional<CommandPlanner> nextPlanner;
    private Map<GameObjectModel, List<Runnable>> commandExecutorMap;

    public CommandPlan clearNextPlanner(){
        nextPlanner = empty();
        return this ;
    }
    public CommandPlan setNextPlanner(CommandPlanner commandPlanner){
        nextPlanner = Optional.of(commandPlanner);
        return this;
    }
}
