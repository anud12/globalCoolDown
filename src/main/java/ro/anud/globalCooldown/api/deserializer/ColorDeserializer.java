package ro.anud.globalCooldown.api.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ColorDeserializer extends StdDeserializer<Color> {
    public ColorDeserializer() {
        super(Color.class);
    }

    @Override
    public Color deserialize(final JsonParser p,
                             final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = p.getCodec().readTree(p);
        return new Color(jsonNode.get("red").asDouble(),
                         jsonNode.get("green").asDouble(),
                         jsonNode.get("blue").asDouble(),
                         1);
    }
}