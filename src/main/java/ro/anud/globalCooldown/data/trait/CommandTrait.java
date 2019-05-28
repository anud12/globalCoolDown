package ro.anud.globalCooldown.data.trait;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.anud.globalCooldown.engine.command.Command;
import ro.anud.globalCooldown.engine.command.planner.CommandPlanner;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CommandTrait implements Trait {
    private List<Command> commandList;
    private List<CommandPlanner> plannerList;

    public CommandTrait() {
        commandList = new ArrayList<>();
        plannerList = new ArrayList<>();
    }

    public void clear() {
        commandList.clear();
        plannerList.clear();
    }

    public void addCommand(final Command command) {
        commandList.add(command);
    }
    public void addPlan(final CommandPlanner commandPlan){
        plannerList.add(commandPlan);
    }
}


