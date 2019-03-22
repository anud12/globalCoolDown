package ro.anud.globalCooldown.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ro.anud.globalCooldown.trait.Trait;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Setter
@Getter
public class GameObjectModel {
    private Map<String, Trait> aspects;

    @Builder
    public GameObjectModel() {
        aspects = new HashMap<>();
    }

    public void addTrait(Trait o) {
        aspects.put(o.getClass().getSimpleName(), o);
    }

    public <T extends Trait> Optional<T> getTrait(Class<T> aspectClazz) {
        try {
            T clazz = (T) aspects.get(aspectClazz.getSimpleName());
            return Optional.ofNullable(clazz);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }
}
