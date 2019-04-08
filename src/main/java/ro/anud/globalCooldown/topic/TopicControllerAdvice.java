package ro.anud.globalCooldown.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
public class TopicControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicControllerAdvice.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    public TopicControllerAdvice(final SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = Objects.requireNonNull(simpMessagingTemplate, "simpMessagingTemplate must not be null");
    }

    @SubscribeMapping("/ws/error-{name}")
    public void handleSubscription(@DestinationVariable("name") final String name) {
        LOGGER.info("handleSubscription " + name);
    }

    @MessageExceptionHandler(RuntimeException.class)
    public void handleException(RuntimeException exception,
                                final SimpMessageHeaderAccessor inHeaderAccessor) {
        simpMessagingTemplate.convertAndSend("/ws/error@" + inHeaderAccessor.getUser().getName(),
                                             Optional.ofNullable(exception.getMessage()).orElse("")
        );
    }
}
