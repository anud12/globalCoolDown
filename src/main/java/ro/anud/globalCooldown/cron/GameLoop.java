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
import ro.anud.globalCooldown.service.TriggerService;
import ro.anud.globalCooldown.trigger.Trigger;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);

    private final MessageSendingOperations<String> messagingTemplate;
    private final GameObjectService gameObjectService;
    private final CommandService commandService;
    private final TriggerService triggerService;
    private final WorldEmitter worldEmitter;

    public GameLoop(MessageSendingOperations messagingTemplate,
                    GameObjectService gameObjectService,
                    final CommandService commandService,
                    final TriggerService triggerService,
                    final WorldEmitter worldEmitter) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
        this.commandService = Objects.requireNonNull(commandService, "commandService must not be null");
        this.triggerService = Objects.requireNonNull(triggerService, "triggerService must not be null");
        this.worldEmitter = Objects.requireNonNull(worldEmitter, "worldEmitter must not be null");
    }

    @Scheduled(fixedRate = 12)
    private void gameLoop() {
        List<GameObjectModel> gameObjectModels = gameObjectService.getAll();
        List<Trigger> triggerList = gameObjectModels
                .stream()
                .parallel()
                .flatMap(gameObjectModel -> commandService.processCommand(gameObjectModel).stream())
                .map(CommandResponse::getTriggerList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        triggerService.processWithNonWorldTriggers(triggerList);

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
