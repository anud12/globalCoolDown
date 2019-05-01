package ro.anud.globalCooldown.api.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ro.anud.globalCooldown.api.exception.TopicMessageException;
import ro.anud.globalCooldown.api.validation.validationChain.ValidationChain;
import ro.anud.globalCooldown.api.validator.CommandValidator;
import ro.anud.globalCooldown.data.factory.GameObjectFactory;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.model.Point;
import ro.anud.globalCooldown.data.repository.GameObjectRepository;
import ro.anud.globalCooldown.data.trait.CommandTrait;
import ro.anud.globalCooldown.engine.command.type.CreateCommand;
import ro.anud.globalCooldown.engine.command.type.MoveCommand;
import ro.anud.globalCooldown.engine.command.type.TeleportCommand;

import java.util.Objects;

@Controller
@MessageMapping("/ws/gameObject")
public class GameObjectTopic {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectTopic.class);

    private final GameObjectRepository gameObjectRepository;
    private final GameObjectFactory gameObjectFactory;
    private final CommandValidator commandValidator;

    public GameObjectTopic(final GameObjectRepository gameObjectRepository,
                           final GameObjectFactory gameObjectFactory,
                           final CommandValidator commandValidator) {
        this.gameObjectRepository = Objects.requireNonNull(gameObjectRepository, "gameObjectRepository must not be null");
        this.gameObjectFactory = Objects.requireNonNull(gameObjectFactory, "gameObjectFactory must not be null");
        this.commandValidator = Objects.requireNonNull(commandValidator, "commandValidator must not be null");
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
                         final SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        GameObjectModel gameObjectModel = gameObjectRepository.getById(id);
        new ValidationChain<>(gameObjectModel)
                .check(commandValidator.isMessageOwnerOfGameObject(simpMessageHeaderAccessor))
                .validate(validationChainResults -> {
                    throw new TopicMessageException(validationChainResults);
                });

        gameObjectModel
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
                     final SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        GameObjectModel gameObjectModel = gameObjectRepository.getById(id);
        new ValidationChain<>(gameObjectModel)
                .check(commandValidator.isMessageOwnerOfGameObject(simpMessageHeaderAccessor))
                .validate(validationChainResults -> {
                    throw new TopicMessageException(validationChainResults);
                });
        LOGGER.info(gameObjectModel.toString());
        gameObjectModel
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
                       final SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        GameObjectModel gameObjectModel = gameObjectRepository.getById(id);
        new ValidationChain<>(gameObjectModel)
                .check(commandValidator.isMessageOwnerOfGameObject(simpMessageHeaderAccessor))
                .validate(validationChainResults -> {
                    throw new TopicMessageException(validationChainResults);
                });

        gameObjectRepository.getById(id)
                .getTrait(CommandTrait.class)
                .ifPresent(commandTrait -> {
                               commandTrait.clear();
                               commandTrait.addCommand(
                                       CreateCommand
                                               .builder()
                                               .newGameObjectModel(gameObjectFactory.loadFromDisk("ship", 5, 5))
                                               .build()
                               );
                           }
                );
    }
}