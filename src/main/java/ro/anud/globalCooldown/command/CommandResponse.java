package ro.anud.globalCooldown.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.anud.globalCooldown.trait.Trait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
public class CommandResponse {
    public List<List<Trait>> createdGameObjectModelTrait;
    public Optional<Command> nextCommand;

    @Builder
    public CommandResponse(final List<List<Trait>> createdGameObjectModelTrait,
                           final Command nextCommand) {


        this.createdGameObjectModelTrait = Optional.ofNullable(createdGameObjectModelTrait).orElseGet(() -> Arrays.asList(new ArrayList<>()));
        this.nextCommand = Optional.ofNullable(nextCommand);
    }
}
