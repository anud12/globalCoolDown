package ro.anud.globalCooldown.api.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import javafx.geometry.Point2D;

import java.io.IOException;

public class Point2DDeserializer extends StdDeserializer<Point2D> {
    public Point2DDeserializer() {
        super(Point2D.class);
    }

    @Override
    public Point2D deserialize(final JsonParser p,
                               final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = p.getCodec().readTree(p);
        return new Point2D(jsonNode.get("x").asDouble(),
                           jsonNode.get("y").asDouble());
    }
}
