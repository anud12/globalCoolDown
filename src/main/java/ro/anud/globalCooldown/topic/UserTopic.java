package ro.anud.globalCooldown.topic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ro.anud.globalCooldown.exception.TopicMessageException;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.service.UserService;
import ro.anud.globalCooldown.validation.validationChain.ValidationChain;
import ro.anud.globalCooldown.validation.validationChain.ValidationChainResult;

import java.util.Objects;
import java.util.Optional;

import static ro.anud.globalCooldown.config.websocket.WebsocketSessionAtributes.CONNECTION_ID;

@Controller
@MessageMapping("/ws/user/")
public class UserTopic {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTopic.class);

    private final UserService userService;
    private final GameObjectService gameObjectService;


    public UserTopic(final UserService userService,
                     final GameObjectService gameObjectService) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
    }

    @SubscribeMapping("/token")
    public String subscribeToken(final SimpMessageHeaderAccessor inHeaderAccessor) {
        return inHeaderAccessor.getSessionAttributes()
                .get(CONNECTION_ID.getKey())
                .toString();
    }

    @MessageMapping("/register")
    public void register(@RequestBody final UserModel userModel) {
        //        if (userService.notExists(userModel)) {
        //            userService.addUser(userModel);
        //            gameObjectService.initializeForUser(userModel);
        //        } else {
        //            throw new RuntimeException("User already exists");
        //        }
        new ValidationChain<>(userModel)
                .check(userModel1 -> {
                    if (!userService.notExists(userModel1)) {
                        return Optional.of(ValidationChainResult.builder()
                                                   .errorCode("User already exists")
                                                   .field("username")
                                                   .build());
                    } else {
                        return Optional.empty();
                    }
                })
                .check(userModel1 -> {
                    if (!userService.notExists(userModel1)) {
                        return Optional.of(ValidationChainResult.builder()
                                                   .errorCode("User already exists")
                                                   .field("password")
                                                   .build());
                    } else {
                        return Optional.empty();
                    }
                })
                .validate(validationChainResults -> {
                    System.out.println("exception");
                    throw new TopicMessageException(validationChainResults);
                });
        userService.addUser(userModel);
        gameObjectService.initializeForUser(userModel);
        //
        //        if (userService.notExists(userModel)) {
        //
        //        } else {
        //            throw new RuntimeException("User already exists");
        //        }
    }

    @MessageMapping("/login")
    public void login(@RequestBody final UserModel userModel,
                      final SimpMessageHeaderAccessor inHeaderAccessor) {
        userService.login(userModel, inHeaderAccessor.getSessionAttributes()
                .get(CONNECTION_ID.getKey())
                .toString()
        );
    }
}
