package ro.anud.globalCooldown.topic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.service.UserService;

import java.util.Objects;

@Controller
@MessageMapping("/ws/user/")
public class UserTopic {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTopic.class);

    private final UserService userService;
    private SimpMessagingTemplate simpMessagingTemplate;


    public UserTopic(final UserService userService,
                     final SimpMessagingTemplate simpMessagingTemplate) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.simpMessagingTemplate = Objects.requireNonNull(simpMessagingTemplate, "simpMessagingTemplate must not be null");
    }

    @SubscribeMapping("/token")
    public String getName(final SimpMessageHeaderAccessor inHeaderAccessor) {
        return inHeaderAccessor.getSessionAttributes().get("name").toString();
    }

    @MessageMapping("/register")
    @SendTo("/register")
    public String register(@RequestBody final UserModel userModel) {
        userService.addUser(userModel);
        return "register";
    }

    @MessageMapping("/login")
    public void login(@RequestBody final UserModel userModel,
                      final SimpMessageHeaderAccessor inHeaderAccessor) {
        userService.login(userModel, inHeaderAccessor.getSessionAttributes()
                .get("name")
                .toString()
        );
    }
}
