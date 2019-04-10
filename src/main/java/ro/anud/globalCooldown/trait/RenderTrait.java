package ro.anud.globalCooldown.trait;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RenderTrait implements Trait {
    private List<Point2D> modelPointList;
    private Color color;
}
