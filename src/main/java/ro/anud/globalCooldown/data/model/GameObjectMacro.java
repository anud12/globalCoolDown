package ro.anud.globalCooldown.data.model;

import lombok.*;
import ro.anud.globalCooldown.data.trait.Trait;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class GameObjectMacro {
    private String definition;
    private Map<Class, Trait> overwriteTraitMap;
}
