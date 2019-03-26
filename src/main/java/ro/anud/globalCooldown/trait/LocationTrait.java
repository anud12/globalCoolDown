package ro.anud.globalCooldown.trait;

import javafx.geometry.Point2D;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class LocationTrait implements Trait {
    final static String NAME = "name";
    private Point2D point2D;
}
