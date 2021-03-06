package ro.anud.globalCooldown.data.trait;

import javafx.geometry.Point2D;
import lombok.*;
import ro.anud.globalCooldown.data.model.RGBA;

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
    private Double furtherPoint;
    private RGBA vertexColor;
    private RGBA polygonColor;
}
