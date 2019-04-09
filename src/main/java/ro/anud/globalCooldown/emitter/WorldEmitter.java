package ro.anud.globalCooldown.emitter;

import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.service.UserService;

import java.util.List;
import java.util.Objects;

@Service
public class WorldEmitter {

    private final UserService userService;
    private final MessageSendingOperations<String> messagingTemplate;

    public WorldEmitter(final UserService userService,
                        final MessageSendingOperations<String> messagingTemplate) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
    }


    public void all(List<GameObjectModel> gameObjectModelList) {
        messagingTemplate.convertAndSend("/ws/world/all", gameObjectModelList);
    }

    public void to(String username, List<GameObjectModel> gameObjectModelList) {
        userService.getConnectionListByName(username)
                .forEach(connectionId -> {
                    String url = "/ws/world@" + connectionId;
                    messagingTemplate.convertAndSend(url, gameObjectModelList);
                });
    }
}
