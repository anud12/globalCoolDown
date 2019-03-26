package ro.anud.globalCooldown.trait;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.anud.globalCooldown.command.Command;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CommandTrait implements Trait {
    private List<Command> commandList;

    public CommandTrait() {
        commandList = new ArrayList<>();
    }

    public void queueCommand(final Command command) {
        commandList.add(command);
    }
}


