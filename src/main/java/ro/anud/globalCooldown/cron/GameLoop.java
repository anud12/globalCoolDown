package ro.anud.globalCooldown.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.command.CommandResponse;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.service.CommandService;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.service.UserService;
import ro.anud.globalCooldown.trait.CommandTrait;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);

    private final MessageSendingOperations<String> messagingTemplate;
    private final GameObjectService gameObjectService;
    private final CommandService commandService;
    private final UserService userService;

    public GameLoop(MessageSendingOperations messagingTemplate,
                    GameObjectService gameObjectService,
                    final CommandService commandService,
                    final UserService userService) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
        this.commandService = Objects.requireNonNull(commandService, "commandService must not be null");
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    @Scheduled(fixedRate = 500)
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
                        commandTrait.clearAndSet(commandResponses
                                                         .stream()
                                                         .map(CommandResponse::getNextCommand)
                                                         .filter(Optional::isPresent)
                                                         .map(Optional::get)
                                                         .collect(Collectors.toList())
                        );

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
        messagingTemplate.convertAndSend("/ws/world/all", gameObjectService.getAll());
        gameObjectService.getAllByOwner()
                .forEach((username, gameObjectModels1) -> {
                    userService.getConnectionListByName(username)
                            .forEach(connection -> {
                                LOGGER.info("Sending " + "/ws/world-" + connection);
                                messagingTemplate.convertAndSend("/ws/world-" + connection, gameObjectModels);
                            });

                });

    }
}
