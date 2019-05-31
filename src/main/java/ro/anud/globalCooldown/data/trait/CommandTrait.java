package ro.anud.globalCooldown.data.trait;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.anud.globalCooldown.data.command.Command;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CommandTrait implements Trait {
    private List<Command> plannerList;

    public CommandTrait() {
        plannerList = new ArrayList<>();
    }

    public void clear() {
        plannerList.clear();
    }
    public void addPlan(final Command commandPlan){
        plannerList.add(commandPlan);
    }
}


