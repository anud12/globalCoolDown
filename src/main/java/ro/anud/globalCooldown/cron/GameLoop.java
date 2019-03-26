package ro.anud.globalCooldown.cron;

import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.command.Command;
import ro.anud.globalCooldown.command.CommandArguments;
import ro.anud.globalCooldown.command.CommandResponse;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.trait.CommandTrait;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameLoop {

    private final MessageSendingOperations<String> messagingTemplate;
    private final GameObjectService gameObjectService;

    public GameLoop(MessageSendingOperations messagingTemplate,
                    GameObjectService gameObjectService) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
    }

    @Scheduled(fixedRate = 25)
    private void gameLoop() {
        GameObjectModel gameObjectModel = gameObjectService.getById(1);
        gameObjectModel.getTrait(CommandTrait.class).
                ifPresent(commandTrait -> {
                            List<Command> commandList = commandTrait
                                    .getCommandList()
                                    .stream()
                                    .map(command -> command.execute(CommandArguments
                                            .builder()
                                            .gameObjectModel(gameObjectModel)
                                            .deltaTime(25L)
                                            .build()))
                                    .map(CommandResponse::getCommand)
                                    .filter(Optional::isPresent)
                                    .map(Optional::get)
                                    .collect(Collectors.toList());
                            commandTrait.setCommandList(commandList);
                        }
                );
        messagingTemplate.convertAndSend("/ws/hello", new Date());
        messagingTemplate.convertAndSend("/ws/world", Arrays.asList(gameObjectService.getById(1)));
    }
}
