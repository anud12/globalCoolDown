package ro.anud.globalCooldown.data.model;

import javafx.geometry.Point2D;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Point {
    private double x;
    private double y;

    public Point2D toPoint2D() {
        return new Point2D(x, y);
    }
}
