package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.StrictMath.min;

@Service
public class PointIsInsidePointList {
    public boolean isInside(List<Point2D> polygon, Point2D p) {
        int n = polygon.size();
        // There must be at least 3 vertices in polygon[]
        if (n < 3) return false;

        // Create a point for line segment from p to infinite
        Point2D extreme = new Point2D(Integer.MAX_VALUE, p.getY());

        // Count intersections of the above line with sides of polygon
        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;

            // Check if the line segment from 'p' to 'extreme' intersects
            // with the line segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon.get(i), polygon.get(next), p, extreme)) {
                // If the point 'p' is colinear with line segment 'i-next',
                // then check if it lies on segment. If it lies, return true,
                // otherwise false
                if (orientation(polygon.get(i), p, polygon.get(next)) == 0)
                    return onSegment(polygon.get(i), p, polygon.get(next));

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return count % 2 == 1;  // Same as (count%2 == 1)
    }

    private boolean onSegment(Point2D p, Point2D q, Point2D r) {
        if (q.getX() <= max(p.getX(), r.getX())
                && q.getX() >= min(p.getX(), r.getX())
                && q.getY() <= max(p.getY(), r.getY())
                && q.getY() >= min(p.getY(), r.getY())
        )
            return true;
        return false;
    }


    private int orientation(Point2D p, Point2D q, Point2D r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // colinear 
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    private boolean doIntersect(Point2D p1, Point2D q1, Point2D p2, Point2D q2) {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and p2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }
}
