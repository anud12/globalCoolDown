package ro.anud.globalCooldown.engine.service;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.Properties;
import ro.anud.globalCooldown.api.validation.optionalValidation.OptionalValidation;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.repository.GameObjectRepository;
import ro.anud.globalCooldown.data.service.WorldService;
import ro.anud.globalCooldown.data.trait.CommandTrait;
import ro.anud.globalCooldown.engine.command.CommandScope;
import ro.anud.globalCooldown.engine.command.planner.CommandPlan;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandService {
    private final CommandScope commandScope;

    public CommandService(final OptionalValidation optionalValidation,
                          final WorldService worldService,
                          final Properties properties,
                          final GameObjectRepository gameObjectRepository) {
        this.commandScope = CommandScope
                .builder()
                .optionalValidation(optionalValidation)
                .worldService(worldService)
                .properties(properties)
                .gameObjectRepository(gameObjectRepository)
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
}
