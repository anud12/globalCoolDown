package ro.anud.globalCooldown.config.websocket;

import java.util.Objects;

public enum WebsocketSessionAtributes {
    CONNECTION_ID("connectionId");

    private String key;

    WebsocketSessionAtributes(final String key) {
        this.key = Objects.requireNonNull(key, "key must not be null");
    }

    public String getKey() {
        return key;
    }
}
