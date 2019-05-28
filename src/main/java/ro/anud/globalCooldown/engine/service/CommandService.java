package ro.anud.globalCooldown.engine.service;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.Properties;
import ro.anud.globalCooldown.api.validation.optionalValidation.OptionalValidation;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.service.WorldService;
import ro.anud.globalCooldown.data.trait.CommandTrait;
import ro.anud.globalCooldown.engine.command.CommandResponse;
import ro.anud.globalCooldown.engine.command.CommandScope;
import ro.anud.globalCooldown.engine.command.planner.CommandPlan;
import ro.anud.globalCooldown.engine.factory.CommandFactory;
import ro.anud.globalCooldown.engine.factory.TriggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommandService {
    private final CommandScope commandScope;

    public CommandService(final OptionalValidation optionalValidation,
                          final CommandFactory commandFactory,
                          final TriggerFactory triggerFactory,
                          final WorldService worldService,
                          final Properties properties) {
        this.commandScope = CommandScope
                .builder()
                .commandFactory(commandFactory)
                .triggerFactory(triggerFactory)
                .optionalValidation(optionalValidation)
                .worldService(worldService)
                .properties(properties)
                .build();
    }

    public List<CommandPlan> processPlan(final GameObjectModel gameObjectModel) {
        return gameObjectModel
                .getTrait(CommandTrait.class)
                .map(commandTrait -> {
                    List<CommandPlan> commandResponseList = commandTrait
                            .getPlannerList()
                            .stream()
                            .sequential()
                            .map(command -> command.plan(gameObjectModel, commandScope))
                            .collect(Collectors.toList());
                    commandTrait.setPlannerList(commandResponseList.stream()
                            .map(CommandPlan::getNextPlanner)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toList())
                    );
                    return commandResponseList;
                })
        .orElse(Collections.emptyList());
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
