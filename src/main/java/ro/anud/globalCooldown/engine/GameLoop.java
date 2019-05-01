package ro.anud.globalCooldown.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.api.emitter.WorldEmitter;
import ro.anud.globalCooldown.api.service.UserService;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.repository.GameObjectRepository;
import ro.anud.globalCooldown.data.service.GameObjectService;
import ro.anud.globalCooldown.engine.command.CommandResponse;
import ro.anud.globalCooldown.engine.service.CommandService;
import ro.anud.globalCooldown.engine.service.TriggerService;
import ro.anud.globalCooldown.engine.trigger.Trigger;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);

    private final MessageSendingOperations<String> messagingTemplate;
    private final CommandService commandService;
    private final TriggerService triggerService;
    private final WorldEmitter worldEmitter;
    private final UserService userService;
    private final GameObjectRepository gameObjectRepository;
    private final GameObjectService gameObjectService;

    public GameLoop(MessageSendingOperations messagingTemplate,
                    final CommandService commandService,
                    final TriggerService triggerService,
                    final WorldEmitter worldEmitter,
                    final UserService userService,
                    final GameObjectRepository gameObjectRepository,
                    final GameObjectService gameObjectService) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
        this.commandService = Objects.requireNonNull(commandService, "commandService must not be null");
        this.triggerService = Objects.requireNonNull(triggerService, "triggerService must not be null");
        this.worldEmitter = Objects.requireNonNull(worldEmitter, "worldEmitter must not be null");
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.gameObjectRepository = Objects.requireNonNull(gameObjectRepository, "gameObjectRepository must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
    }

    @Scheduled(fixedRateString = "${ro.anud.global-cooldown.properties.deltaTime}")
    private void gameLoop() {
        List<GameObjectModel> gameObjectModels = gameObjectRepository.getAll();
        List<Trigger> triggerList = gameObjectModels
                .stream()
                .parallel()
                .flatMap(gameObjectModel -> commandService.processCommand(gameObjectModel).stream())
                .map(CommandResponse::getTriggerList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        triggerService.processWithNonWorldTriggers(triggerList);

        messagingTemplate.convertAndSend("/ws/hello", new Date());
        worldEmitter.all(gameObjectRepository.getAll()
                                 .stream()
                                 .parallel()
                                 .peek(gameObjectService::buildRender)
                                 .collect(Collectors.toList())
        );

        userService.getUserModelList()
                .forEach(userModel -> worldEmitter
                        .to(userModel.getUsername(),
                            gameObjectRepository.getAllByOwner()
                                    .getOrDefault(userModel.getUsername(),
                                                  Arrays.asList()
                                    )
                        )
                );

    }
}
