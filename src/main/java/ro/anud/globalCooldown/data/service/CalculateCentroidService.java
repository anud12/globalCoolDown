package ro.anud.globalCooldown.data.service;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateCentroidService {

    public Point2D calculateCentroid(List<Point2D> polygon) {
        Point2D center = new Point2D(0, 0);
        for (int i = 0; i < polygon.size() - 1; i++) {
            center = center.add(calculateX(polygon.get(i), polygon.get(i + 1)),
                    calculateY(polygon.get(i), polygon.get(i + 1))
            );
        }
        return new Point2D(center.getX() / (6 * calculateArea(polygon)),
                center.getY() / (6 * calculateArea(polygon))
        );
    }

    private Double calculateX(Point2D i, Point2D i2) {
        return (i.getX() + i2.getX())
                *
                (i.getX() * i2.getY() - i2.getX() * i.getY());
    }

    private Double calculateY(Point2D i, Point2D i2) {
        return (i.getY() + i2.getY())
                *
                (i.getX() * i2.getY() - i2.getX() * i.getY());
    }


    private Double calculateArea(List<Point2D> polygon) {
        double sum = 0;
        for (int i = 0; i < polygon.size() - 1; i++) {
            sum += polygon.get(i).getX() * polygon.get(i + 1).getY()
                    -
                    polygon.get(i + 1).getX() * polygon.get(i).getY();
        }
        return sum / 2;
    }
}
