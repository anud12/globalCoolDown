package ro.anud.globalCooldown.api.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WorldTopic {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldTopic.class);

    @SubscribeMapping("/ws/world/all")
    public void all(SimpMessageHeaderAccessor simpMessageHeaderAccessor,
                    Message message) throws InterruptedException {
        LOGGER.info("/ws/world/all");
        LOGGER.info(simpMessageHeaderAccessor.getSessionAttributes().toString());
        LOGGER.info(simpMessageHeaderAccessor.getSubscriptionId());
        Thread.sleep(1000);
        LOGGER.info("Sad face");
        throw new RuntimeException("sad face");
    }
}
