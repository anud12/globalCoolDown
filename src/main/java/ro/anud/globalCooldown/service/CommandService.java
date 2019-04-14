package ro.anud.globalCooldown.service;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.builder.CommandBuilder;
import ro.anud.globalCooldown.builder.TriggerBuilder;
import ro.anud.globalCooldown.command.CommandArguments;
import ro.anud.globalCooldown.command.CommandResponse;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.validation.optionalValidation.OptionalValidation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommandService {
    private final OptionalValidation optionalValidation;
    private final CommandBuilder commandBuilder;
    private final TriggerBuilder triggerBuilder;

    public CommandService(final OptionalValidation optionalValidation,
                          final CommandBuilder commandBuilder,
                          final TriggerBuilder triggerBuilder) {
        this.optionalValidation = Objects.requireNonNull(optionalValidation, "optionalValidation must not be null");
        this.commandBuilder = Objects.requireNonNull(commandBuilder, "commandBuilder must not be null");
        this.triggerBuilder = Objects.requireNonNull(triggerBuilder, "triggerBuilder must not be null");
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
                                                 .commandBuilder(commandBuilder)
                                                 .triggerBuilder(triggerBuilder)
                                                 .optionalValidation(optionalValidation)
                                                 .gameObjectModel(gameObjectModel)
                                                 .deltaTime(15L)
                                                 .build()
                                )
                        )
                        .collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }
}
