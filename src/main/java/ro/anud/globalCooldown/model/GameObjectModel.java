package ro.anud.globalCooldown.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ro.anud.globalCooldown.trait.Trait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Setter
@Getter
public class GameObjectModel {
    private Map<String, Trait> traitMap;

    public GameObjectModel() {
        traitMap = new HashMap<>();
    }

    @Builder
    public GameObjectModel(List<Trait> traitList) {
        traitMap = new HashMap<>();
        this.addAll(traitList);
    }


    public void addAll(List<Trait> traitList) {
        traitList.forEach(trait -> this.traitMap
                .put(trait.getClass().getSimpleName(), trait)
        );
    }

    public void addTrait(Trait trait) {
        this.traitMap.put(trait.getClass().getSimpleName(), trait);
    }

    public <T extends Trait> Optional<T> getTrait(Class<T> aspectClazz) {
        try {
            T clazz = (T) traitMap.get(aspectClazz.getSimpleName());
            return Optional.ofNullable(clazz);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }
}
