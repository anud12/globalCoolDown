package ro.anud.globalCooldown.service;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.command.CommandArguments;
import ro.anud.globalCooldown.command.CommandResponse;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.validation.OptionalValidation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommandService {
    private final OptionalValidation optionalValidation;

    public CommandService(final OptionalValidation optionalValidation) {
        this.optionalValidation = Objects.requireNonNull(optionalValidation, "optionalValidation must not be null");
    }

    public List<CommandResponse> processCommand(final GameObjectModel gameObjectModel) {
        return gameObjectModel
                .getTrait(CommandTrait.class)
                .map(commandTrait -> commandTrait
                        .getCommandList()
                        .stream()
                        .sequential()
                        .map(command -> command
                                .execute(CommandArguments
                                        .builder()
                                        .optionalValidation(optionalValidation)
                                        .gameObjectModel(gameObjectModel)
                                        .deltaTime(1000L)
                                        .build()
                                )
                        )
                        .collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }
}
