package ro.anud.globalCooldown.cron;

import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class GameLoop {

    private final MessageSendingOperations<String> messagingTemplate;

    public GameLoop(MessageSendingOperations messagingTemplate) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "messagingTemplate must not be null");
    }

    @Scheduled(fixedRate = 15)
    private void gameLoop() {
        messagingTemplate.convertAndSend("/ws/hello", new Date());
    }
}
