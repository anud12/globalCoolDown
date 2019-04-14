package ro.anud.globalCooldown.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.trigger.Trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
public class CommandResponse {
    public List<Trigger> triggerList;
    public Optional<Command> nextCommand;

    public CommandResponse setTriggerList(List<Trigger> triggerList) {
        this.triggerList = triggerList;
        return this;
    }

    public CommandResponse setNextCommand(Optional<Command> nextCommand) {
        this.nextCommand = nextCommand;
        return this;
    }

    @Builder
    public CommandResponse(final List<Trigger> triggerList,
                           final Command nextCommand) {
        this.triggerList = Optional.ofNullable(triggerList).orElseGet(ArrayList::new);
        this.nextCommand = Optional.ofNullable(nextCommand);
    }
}
