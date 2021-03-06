package ro.anud.globalCooldown.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.api.emitter.WorldEmitter;
import ro.anud.globalCooldown.api.service.UserService;
import ro.anud.globalCooldown.data.command.CommandPlan;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.repository.GameObjectRepository;
import ro.anud.globalCooldown.data.service.CommandService;
import ro.anud.globalCooldown.data.service.GameObjectService;
import ro.anud.globalCooldown.data.service.WorldService;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);

    private final MessageSendingOperations<String> messagingTemplate;
    private final CommandService commandService;
    private final WorldEmitter worldEmitter;
    private final UserService userService;
    private final GameObjectRepository gameObjectRepository;
    private final GameObjectService gameObjectService;
    private WorldService worldService;
    private Executor executor = Executors.newCachedThreadPool();

    public GameLoop(MessageSendingOperations messagingTemplate,
                    final CommandService commandService,
                    final WorldEmitter worldEmitter,
                    final UserService userService,
                    final GameObjectRepository gameObjectRepository,
                    final GameObjectService gameObjectService,
                    final WorldService worldService) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
        this.commandService = Objects.requireNonNull(commandService, "commandService must not be null");
        this.worldEmitter = Objects.requireNonNull(worldEmitter, "worldEmitter must not be null");
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.gameObjectRepository = Objects.requireNonNull(gameObjectRepository, "gameObjectRepository must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
        this.worldService = Objects.requireNonNull(worldService, "worldService must not be null");
    }

    @Scheduled(fixedDelayString = "${ro.anud.global-cooldown.properties.deltaTime}")
    private void gameLoop() {

        List<GameObjectModel> gameObjectModels = gameObjectRepository.getAll();
        Map<Object, List<Runnable>> commandPlanList = gameObjectModels
                .stream()
                .map(commandService::processPlan)
                .flatMap(Collection::stream)
                .reduce(CommandPlan::merge)
                .map(commandPlan -> commandPlan.merge(worldService.processPlan()))
                .map(CommandPlan::getCommandExecutorMap)
                .orElseGet(() -> (Map<Object, List<Runnable>>) Collections.EMPTY_MAP);

        commandPlanList.values()
                .parallelStream()
                .forEach(runnables -> runnables.forEach(Runnable::run));
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
