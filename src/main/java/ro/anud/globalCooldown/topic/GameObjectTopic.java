package ro.anud.globalCooldown.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ro.anud.globalCooldown.command.CreateCommand;
import ro.anud.globalCooldown.command.MoveCommand;
import ro.anud.globalCooldown.command.TeleportCommand;
import ro.anud.globalCooldown.model.Point;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.trait.CommandTrait;

import java.util.Objects;

@Controller
@MessageMapping("/ws/gameObject")
public class GameObjectTopic {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectTopic.class);

    private final GameObjectService gameObjectService;

    public GameObjectTopic(final GameObjectService gameObjectService) {
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
    }

    @MessageMapping("{id}")
    public void gameObject(@DestinationVariable("id") final String id,
                           final SimpMessageHeaderAccessor headerAccessor) {
        LOGGER.info(headerAccessor.getSessionAttributes().toString());
        LOGGER.info(id);
    }

    @MessageMapping("{id}/action/teleport")
    public void teleport(@DestinationVariable("id") final Long id,
                         @RequestBody Point point,
                         final MessageHeaders headerAccessor) {
        gameObjectService.getById(id)
                .getTrait(CommandTrait.class)
                .ifPresent(commandTrait -> {
                               commandTrait.clear();
                               commandTrait.addCommand(
                                       TeleportCommand
                                               .builder()
                                               .x(point.getX())
                                               .y(point.getY())
                                               .build()
                               );
                           }
                );
    }


    @MessageMapping("{id}/action/move")
    public void move(@DestinationVariable("id") final Long id,
                     @RequestBody Point point,
                     final SimpMessageHeaderAccessor headerAccessor) {
        gameObjectService.getById(id)
                .getTrait(CommandTrait.class)
                .ifPresent(commandTrait -> {
                               commandTrait.clear();
                               commandTrait.addCommand(
                                       MoveCommand
                                               .builder()
                                               .destinationLocation(point.toPoint2D())
                                               .build()
                               );
                           }
                );
    }

    @MessageMapping("{id}/action/create")
    public void create(@DestinationVariable("id") final Long id,
                       final SimpMessageHeaderAccessor headerAccessor) {
        gameObjectService.getById(id)
                .getTrait(CommandTrait.class)
                .ifPresent(commandTrait -> {
                               commandTrait.clear();
                               commandTrait.addCommand(
                                       CreateCommand
                                               .builder()
                                               .build()
                               );
                           }
                );
    }
}
