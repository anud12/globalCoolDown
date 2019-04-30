package ro.anud.globalCooldown.trait;

import javafx.geometry.Point2D;
import lombok.*;
import ro.anud.globalCooldown.model.RGBA;

import java.util.List;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RenderTrait implements Trait {
    private List<Point2D> modelPointList;
    private RGBA vertexColor;
    private RGBA polygonColor;
}
