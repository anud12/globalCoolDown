package ro.anud.globalCooldown.trait;

import javafx.geometry.Point2D;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class LocationTrait implements Trait {
    private Point2D point2D;
    private Double angle;
    private List<Point2D> modelVertices;
}
