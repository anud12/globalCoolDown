package ro.anud.globalCooldown.api.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static ro.anud.globalCooldown.api.config.websocket.WebsocketSessionAtributes.CONNECTION_ID;

public class AnonymousHandshakeHandler extends DefaultHandshakeHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousHandshakeHandler.class);
    private AtomicInteger id = new AtomicInteger(0);

    @Nullable
    protected Principal determineUser(
            ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String name = id.addAndGet(1) + "";
        attributes.put(CONNECTION_ID.getKey(), name);
        LOGGER.info("determineUser :" + name);
        return () -> name;
    }
}
