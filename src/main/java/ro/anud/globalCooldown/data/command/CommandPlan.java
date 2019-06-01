package ro.anud.globalCooldown.data.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ro.anud.globalCooldown.data.model.GameObjectModel;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static java.util.Optional.empty;

@Getter
@AllArgsConstructor
public class CommandPlan {
    private static Optional empty = empty();

    public static CommandPlan end() {
        return new CommandPlan(empty(), new HashMap<>());
    }

    public static CommandPlan untargetedInstruction(final Runnable instruction) {
        Map<Object, List<Runnable>> map = new HashMap<>();
        map.put(empty, asList(instruction));
        return new CommandPlan(empty(), map);
    }

    public static CommandPlan singleInstruction(final GameObjectModel target,
                                                final Runnable instruction) {
        Map<Object, List<Runnable>> map = new HashMap<>();
        map.put(target, asList(instruction));
        return new CommandPlan(empty(), map);
    }

    private Optional<Command> nextPlanner;
    private Map<Object, List<Runnable>> commandExecutorMap;

    public CommandPlan clearNextPlanner() {
        nextPlanner = empty();
        return this;
    }

    public CommandPlan setNextPlanner(Command command) {
        nextPlanner = Optional.of(command);
        return this;
    }

    public CommandPlan merge(final CommandPlan commandPlan) {
        Map<Object, List<Runnable>> map = commandPlan.commandExecutorMap;
        map.forEach((gameObjectModel, runnables) -> commandExecutorMap
                .merge(gameObjectModel,
                        runnables,
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }));
        return this;
    }

}
