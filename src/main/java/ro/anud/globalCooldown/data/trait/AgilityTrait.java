package ro.anud.globalCooldown.data.trait;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AgilityTrait implements Trait {
    private Double rotationRate;
    private Double translationRate;
}
