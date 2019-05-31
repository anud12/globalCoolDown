package ro.anud.globalCooldown.data.trait;

import javafx.geometry.Point2D;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class LocationTrait implements Trait {
    private Point2D point2D;
    private Double angle;
}
