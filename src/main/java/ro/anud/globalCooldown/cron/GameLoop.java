package ro.anud.globalCooldown.cron;

import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.aspect.LocationAspect;
import ro.anud.globalCooldown.aspect.MetaAspect;
import ro.anud.globalCooldown.model.GameObjectModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Service
public class GameLoop {

    private final MessageSendingOperations<String> messagingTemplate;
    double i = 0;

    public GameLoop(MessageSendingOperations messagingTemplate) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
    }

    @Scheduled(fixedRate = 25)
    private void gameLoop() {
        if (i > 500) {
            i = 0;
        }
        i += 2.5;

        GameObjectModel gameObjectModel = new GameObjectModel();
        gameObjectModel.addAspect(LocationAspect.builder()
                .x(0 + i)
                .y(0 + i)
                .build()
        );

        gameObjectModel.addAspect(MetaAspect.builder()
                .id(0L)
                .name("Object Name")
                .build()
        );


        GameObjectModel gameObjectModel2 = new GameObjectModel();
        gameObjectModel2.addAspect(LocationAspect.builder()
                .x(10 + i)
                .y(10 + i)
                .build()
        );

        gameObjectModel2.addAspect(MetaAspect.builder()
                .id(0L)
                .name("Second Name")
                .build()
        );


        GameObjectModel gameObjectModel3 = new GameObjectModel();
        gameObjectModel3.addAspect(LocationAspect.builder()
                .x(20 + i)
                .y(20 + i)
                .build()
        );

        gameObjectModel3.addAspect(MetaAspect.builder()
                .id(0L)
                .name("Third")
                .build()
        );


        messagingTemplate.convertAndSend("/ws/hello", new Date());
        messagingTemplate.convertAndSend("/ws/world", Arrays.asList(gameObjectModel, gameObjectModel2, gameObjectModel3)
        );
    }
}
