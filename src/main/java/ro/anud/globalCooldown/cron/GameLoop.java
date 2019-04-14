package ro.anud.globalCooldown.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.command.CommandResponse;
import ro.anud.globalCooldown.emitter.WorldEmitter;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.service.CommandService;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.service.WorldService;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.trigger.Trigger;
import ro.anud.globalCooldown.trigger.TriggerArguments;
import ro.anud.globalCooldown.trigger.TriggerResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);

    private final MessageSendingOperations<String> messagingTemplate;
    private final GameObjectService gameObjectService;
    private final CommandService commandService;
    private final WorldService worldService;
    private final WorldEmitter worldEmitter;

    public GameLoop(MessageSendingOperations messagingTemplate,
                    GameObjectService gameObjectService,
                    final CommandService commandService,
                    final WorldService worldService, final WorldEmitter worldEmitter) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
        this.commandService = Objects.requireNonNull(commandService, "commandService must not be null");
        this.worldService = Objects.requireNonNull(worldService, "worldService must not be null");
        this.worldEmitter = Objects.requireNonNull(worldEmitter, "worldEmitter must not be null");
    }

    @Scheduled(fixedRate = 100)
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
                    .ifPresent(commandTrait -> commandTrait.clearAndSet(
                            commandResponses
                                    .stream()
                                    .map(CommandResponse::getNextCommand)
                                    .filter(Optional::isPresent)
                                    .map(Optional::get)
                                    .collect(Collectors.toList())
                               )
                    )
            );
            commandResponses.forEach(commandResponse -> commandResponse
                    .triggerList
                    .forEach(trigger -> trigger.execute(TriggerArguments
                                                                .builder()
                                                                .gameObjectService(this.gameObjectService)
                                                                .worldService(this.worldService)
                                                                .build()))
            );
        });

        List<Trigger> worldTriggerList = worldService.triggerList()
                .stream()
                .map(trigger -> trigger.execute(TriggerArguments
                                                        .builder()
                                                        .gameObjectService(this.gameObjectService)
                                                        .worldService(this.worldService)
                                                        .build())
                )
                .map(TriggerResponse::getNextTrigger)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        worldService.setTriggerList(worldTriggerList);

        messagingTemplate.convertAndSend("/ws/hello", new Date());
        worldEmitter.all(gameObjectService.getAll()
                                 .stream()
                                 .parallel()
                                 .peek(gameObjectService::buildRender)
                                 .collect(Collectors.toList())
        );
        gameObjectService.getAllByOwner()
                .forEach((s, gameObjectModels1) -> worldEmitter
                        .to(s,
                            gameObjectModels1
                                    .stream()
                                    .parallel()
                                    .peek(gameObjectService::buildRender)
                                    .collect(Collectors.toList())));

    }
}
