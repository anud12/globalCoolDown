package ro.anud.globalCooldown.engine.command.type;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.engine.command.Command;
import ro.anud.globalCooldown.engine.command.CommandResponse;
import ro.anud.globalCooldown.engine.command.CommandScope;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class DelayCommand implements Command {

    private final Double time;
    private Double passedTime;
    private Double completePercentage;
    private final Command next;

    @Builder
    public DelayCommand(final Double time,
                        final Command next) {
        this.time = Objects.requireNonNull(time, "time must not be null");
        this.passedTime = 0D;
        this.next = Objects.requireNonNull(next, "next must not be null");
    }


    @Override
    public CommandResponse execute(final CommandScope commandScope,
                                   final GameObjectModel gameObjectModel) {
        passedTime += commandScope.getProperties().getDeltaTime();
        completePercentage = passedTime / time;
        if (passedTime > time) {
            return next.execute(commandScope, gameObjectModel);
        }
        return CommandResponse.builder()
                .nextCommand(this)
                .triggerList(null)
                .build();
    }
}
