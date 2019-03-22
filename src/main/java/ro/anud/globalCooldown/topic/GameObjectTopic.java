package ro.anud.globalCooldown.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ro.anud.globalCooldown.command.TeleportCommand;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.trait.LocationTrait;

import java.util.Objects;

@Controller
public class GameObjectTopic {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectTopic.class);

    private final GameObjectService gameObjectService;

    public GameObjectTopic(final GameObjectService gameObjectService) {
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
    }

    @MessageMapping("/ws/gameObject/{id}")
    public void gameObject(@DestinationVariable("id") final String id,
                           final SimpMessageHeaderAccessor headerAccessor) {
        LOGGER.info(headerAccessor.getSessionAttributes().toString());
        LOGGER.info(id);
    }

    @MessageMapping("/ws/gameObject/queue/{command}")
    public void queueCommand(@DestinationVariable("command") final String command,
                             @RequestBody LocationTrait locationTrait,
                             final SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(command + ": " + locationTrait);
        gameObjectService.getById(1).getTrait(CommandTrait.class).ifPresent(commandTrait -> {
            commandTrait.queueCommand(TeleportCommand.builder()
                    .x(locationTrait.getX())
                    .y(locationTrait.getY())
                    .build());
        });

        System.out.println(gameObjectService.getById(1).getTrait(CommandTrait.class).get());
    }
}
