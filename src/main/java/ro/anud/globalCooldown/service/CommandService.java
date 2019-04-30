package ro.anud.globalCooldown.service;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.factory.CommandFactory;
import ro.anud.globalCooldown.factory.TriggerFactory;
import ro.anud.globalCooldown.command.CommandResponse;
import ro.anud.globalCooldown.command.CommandScope;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.validation.optionalValidation.OptionalValidation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandService {
    private final CommandScope commandScope;

    public CommandService(final OptionalValidation optionalValidation,
                          final CommandFactory commandFactory,
                          final TriggerFactory triggerFactory,
                          final WorldService worldService) {
        this.commandScope = CommandScope
                .builder()
                .commandFactory(commandFactory)
                .triggerFactory(triggerFactory)
                .optionalValidation(optionalValidation)
                .worldService(worldService)
                .deltaTime(50L)
                .build();
    }

    public List<CommandResponse> processCommand(final GameObjectModel gameObjectModel) {
        return gameObjectModel
                .getTrait(CommandTrait.class)
                .map(commandTrait -> {
                    List<CommandResponse> commandResponseList = commandTrait
                            .getCommandList()
                            .stream()
                            .sequential()
                            .map(command -> command.execute(commandScope, gameObjectModel))
                            .collect(Collectors.toList());
                    commandTrait.setCommandList(commandResponseList.stream()
                                                        .map(CommandResponse::getNextCommand)
                                                        .filter(Optional::isPresent)
                                                        .map(Optional::get)
                                                        .collect(Collectors.toList())
                    );
                    return commandResponseList;
                })
                .orElseGet(Collections::emptyList);
    }
}
