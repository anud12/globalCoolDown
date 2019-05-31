package ro.anud.globalCooldown.api.topic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ro.anud.globalCooldown.api.exception.TopicMessageException;
import ro.anud.globalCooldown.data.model.UserModel;
import ro.anud.globalCooldown.data.service.GameObjectService;
import ro.anud.globalCooldown.api.service.UserService;
import ro.anud.globalCooldown.api.validation.validationChain.ValidationChain;
import ro.anud.globalCooldown.api.validator.UserValidator;

import java.util.Objects;

import static ro.anud.globalCooldown.api.config.websocket.WebsocketSessionAtributes.CONNECTION_ID;

@Controller
@MessageMapping("/ws/user/")
public class UserTopic {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTopic.class);

    private final UserService userService;
    private final GameObjectService gameObjectService;
    private final UserValidator userValidator;


    public UserTopic(final UserService userService,
                     final GameObjectService gameObjectService,
                     final UserValidator userValidator) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
        this.userValidator = Objects.requireNonNull(userValidator, "userValidator must not be null");
    }

    @SubscribeMapping("/token")
    public String subscribeToken(final SimpMessageHeaderAccessor inHeaderAccessor) {
        return inHeaderAccessor.getSessionAttributes()
                .get(CONNECTION_ID.getKey())
                .toString();
    }

    @MessageMapping("/register")
    public void register(@RequestBody final UserModel userModel) {
        new ValidationChain<>(userModel)
                .check(userValidator::usernameNotEmpty)
                .check(userValidator::usernameIsUnique)
                .validate(validationChainResults -> {
                    throw new TopicMessageException(validationChainResults);
                });
        userService.addUser(userModel);
        gameObjectService.initializeForUser(userModel);
    }

    @MessageMapping("/login")
    public void login(@RequestBody final UserModel userModel,
                      final SimpMessageHeaderAccessor inHeaderAccessor) {
        String connectionId = inHeaderAccessor.getSessionAttributes()
                .get(CONNECTION_ID.getKey())
                .toString();
        userService.logout(connectionId);
        userService.login(userModel, connectionId);
    }
}
