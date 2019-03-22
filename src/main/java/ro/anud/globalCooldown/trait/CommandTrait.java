package ro.anud.globalCooldown.trait;


import lombok.Builder;
import lombok.ToString;
import ro.anud.globalCooldown.command.Command;

import java.util.ArrayList;
import java.util.List;

@ToString
public class CommandTrait implements Trait {
    private final ArrayList<Command> commandList;

    public CommandTrait() {
        commandList = new ArrayList<Command>();
    }

    public void queueCommand(final Command command) {
        commandList.add(command);
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
