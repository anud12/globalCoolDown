package ro.anud.globalCooldown.trait;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class LocationTrait implements Trait {
    final static String NAME = "name";
    private double x;
    private double y;
}
