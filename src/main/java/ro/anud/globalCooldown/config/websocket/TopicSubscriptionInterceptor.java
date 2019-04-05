package ro.anud.globalCooldown.config.websocket;

import org.slf4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Optional;

public class TopicSubscriptionInterceptor implements ChannelInterceptor {
    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TopicSubscriptionInterceptor.class);


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (Optional.ofNullable(headerAccessor.getDestination())
                .orElse("")
                .equalsIgnoreCase("/ws/user/login")) {
            return null;
        }
        return message;
    }
}
