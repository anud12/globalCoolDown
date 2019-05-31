package ro.anud.globalCooldown.data.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.api.deserializer.ColorDeserializer;
import ro.anud.globalCooldown.api.deserializer.Point2DDeserializer;
import ro.anud.globalCooldown.data.model.GameObjectMacro;
import ro.anud.globalCooldown.data.trait.Trait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TraitMapFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraitMapFactory.class);

    private final ObjectMapper objectMapper;

    private final Function<String, File> toMacro = (string) -> new File("src/main/resources/gameObject/macro/"
                                                                                + string
                                                                                + ".macro.json");
    private final Function<String, File> toDefinition = (string) -> new File("src/main/resources/gameObject/definition/"
                                                                                     + string
                                                                                     + ".def.json");

    public TraitMapFactory(final ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper must not be null");
        objectMapper.registerModule(new SimpleModule().addDeserializer(Point2D.class, new Point2DDeserializer()));
        objectMapper.registerModule(new SimpleModule().addDeserializer(Color.class, new ColorDeserializer()));
    }

    public List<Map<Class, Trait>> getMacro(String string) {
        try {
            LOGGER.info("getMacro " + string + " -> " + toDefinition.apply(string));

            List<GameObjectMacro> gameObjectMacroList = objectMapper.readValue(toMacro.apply(string),
                                                                               new TypeReference<List<GameObjectMacro>>() {});
            return gameObjectMacroList.stream()
                    .map(gameObjectMacro -> {
                        Map<Class, Trait> traitMap = getDefinition(gameObjectMacro.getDefinition());
                        traitMap.putAll(gameObjectMacro.getOverwriteTraitMap());
                        return traitMap;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Map<Class, Trait> getDefinition(String string) {
        try {
            LOGGER.info("getDefinition " + string + " -> " + toDefinition.apply(string));
            return objectMapper.readValue(toDefinition.apply(string),
                                          new TypeReference<Map<Class, Trait>>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
