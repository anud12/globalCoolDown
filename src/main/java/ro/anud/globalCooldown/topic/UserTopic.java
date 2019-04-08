package ro.anud.globalCooldown.topic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.service.GameObjectService;
import ro.anud.globalCooldown.service.UserService;

import java.util.Objects;

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
        userService.addUser(userModel);
        gameObjectService.initializeForUser(userModel);
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
