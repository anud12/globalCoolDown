package ro.anud.globalCooldown.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.trait.Trait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
public class CommandResponse {
    public final List<List<Trait>> createdGameObjectModelTrait;
    public final Optional<Command> command;

    @Builder
    public CommandResponse(final List<List<Trait>> createdGameObjectModelTrait,
                           final Command command) {


        this.createdGameObjectModelTrait = Optional.ofNullable(createdGameObjectModelTrait).orElseGet(() -> Arrays.asList(new ArrayList<>()));
        this.command = Optional.ofNullable(command);
    }
}
