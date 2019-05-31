package ro.anud.globalCooldown.api.config.websocket;

import org.slf4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import static java.util.Optional.ofNullable;
import static ro.anud.globalCooldown.api.config.websocket.WebsocketSessionAtributes.CONNECTION_ID;

public class TopicSubscriptionInterceptor implements ChannelInterceptor {
    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TopicSubscriptionInterceptor.class);


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        String destination = ofNullable(headerAccessor.getDestination())
                .orElse("");
        String connectionSessionId = (String) ofNullable(headerAccessor.getSessionAttributes()
                                                                 .get(CONNECTION_ID.getKey()))
                .orElse("");
        LOGGER.info("Inbound[" + connectionSessionId + "]: " + destination);
        String[] substring = destination.split("@");
        if (substring.length > 1
                && !substring[substring.length - 1].equals(connectionSessionId)) {
            LOGGER.info("Blocked :" + destination);
            return null;
        }
        return message;
    }
}
