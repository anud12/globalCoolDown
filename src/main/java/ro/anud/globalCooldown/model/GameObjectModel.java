package ro.anud.globalCooldown.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Setter
@Getter
public class GameObjectModel {
    private Map<String, Object> aspects;

    @Builder
    public GameObjectModel() {
        aspects = new HashMap<>();
    }

    public void addAspect(Object o) {
        aspects.put(o.getClass().getSimpleName(), o);
    }

    public <T> Optional<T> getAspect(Class<T> aspectClazz) {
        try {
            T clazz = (T) aspects.get(aspectClazz.getSimpleName());
            return Optional.ofNullable(clazz);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }
}
