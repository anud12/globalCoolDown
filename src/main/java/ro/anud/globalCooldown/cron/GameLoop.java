package ro.anud.globalCooldown.cron;

import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.command.CommandResponse;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.service.CommandService;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.trait.CommandTrait;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private final MessageSendingOperations<String> messagingTemplate;
    private final GameObjectService gameObjectService;
    private final CommandService commandService;

    public GameLoop(MessageSendingOperations messagingTemplate,
                    GameObjectService gameObjectService,
                    final CommandService commandService) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
        this.commandService = Objects.requireNonNull(commandService, "commandService must not be null");
    }

    @Scheduled(fixedRate = 25)
    private void gameLoop() {
        List<GameObjectModel> gameObjectModels = gameObjectService.getAll();
        Map<GameObjectModel, List<CommandResponse>> commandResponseList = gameObjectModels
                .stream()
                .map(gameObjectModel -> new AbstractMap.SimpleEntry<>(gameObjectModel,
                                commandService.processCommand(gameObjectModel)
                        )
                )
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue)
                );
        commandResponseList.forEach((gameObjectModel, commandResponses) -> {
            commandResponses.forEach(commandResponse -> gameObjectModel
                    .getTrait(CommandTrait.class)
                    .ifPresent(commandTrait -> {
                        commandTrait.clear();
                        commandResponses.stream()
                                .map(CommandResponse::getCommand)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .forEach(commandTrait::addCommand);
                    })
            );
            commandResponses.forEach(commandResponse -> commandResponse
                    .createdGameObjectModelTrait
                    .stream()
                    .filter(list -> !list.isEmpty())
                    .forEach(gameObjectService::create)
            );
        });
        messagingTemplate.convertAndSend("/ws/hello", new Date());
        messagingTemplate.convertAndSend("/ws/world", gameObjectService.getAll());
    }
}
