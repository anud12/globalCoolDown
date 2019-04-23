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
@AllArgsConstructor
@NoArgsConstructor
public class ModelTrait implements Trait {
    private List<Point2D> vertexPointList;
    private Double angleOffset;
    private Color vertexColor;
}
