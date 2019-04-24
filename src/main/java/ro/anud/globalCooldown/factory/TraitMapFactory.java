package ro.anud.globalCooldown.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.deserializer.ColorDeserializer;
import ro.anud.globalCooldown.deserializer.Point2DDeserializer;
import ro.anud.globalCooldown.trait.Trait;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class TraitMapFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraitMapFactory.class);

    private final ObjectMapper objectMapper;
    private final Function<String, File> toLocalFile = (string) -> new File("src/main/resources/gameObjectFactory/"
                                                                                    + string
                                                                                    + ".json");

    public TraitMapFactory(final ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper must not be null");
        objectMapper.registerModule(new SimpleModule().addDeserializer(Point2D.class, new Point2DDeserializer()));
        objectMapper.registerModule(new SimpleModule().addDeserializer(Color.class, new ColorDeserializer()));
    }

    public Map<Class, Trait> getType(String string) {
        try {
            LOGGER.info("getType " + string + " -> " + toLocalFile.apply(string));
            return objectMapper.readValue(toLocalFile.apply(string),
                                          new TypeReference<Map<Class, Trait>>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
