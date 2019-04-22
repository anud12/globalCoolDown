package ro.anud.globalCooldown.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.anud.globalCooldown.trait.Trait;

import java.util.*;

@Setter
@Getter
@ToString
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


    public void addAll(Collection<Trait> traitList) {
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
