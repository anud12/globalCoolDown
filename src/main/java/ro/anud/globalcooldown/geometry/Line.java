package ro.anud.globalcooldown.geometry;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class Line {


    private Point start;
    private Point end;

    public static Optional<Point> findIntersection(Line L1, Line L2) {
        // Denominator for ua and ub are the same, so store this calculation
        double d =
                (L2.getEnd().getY() - L2.getStart().getY()) * (L1.getEnd().getX() - L1.getStart().getX())
                        -
                        (L2.getEnd().getX() - L2.getStart().getX()) * (L1.getEnd().getY() - L1.getStart().getY());

        //n_a and n_b are calculated as seperate values for readability
        double n_a =
                (L2.getEnd().getX() - L2.getStart().getX()) * (L1.getStart().getY() - L2.getStart().getY())
                        -
                        (L2.getEnd().getY() - L2.getStart().getY()) * (L1.getStart().getX() - L2.getStart().getX());

        double n_b =
                (L1.getEnd().getX() - L1.getStart().getX()) * (L1.getStart().getY() - L2.getStart().getY())
                        -
                        (L1.getEnd().getY() - L1.getStart().getY()) * (L1.getStart().getX() - L2.getStart().getX());

        // Make sure there is not a division by zero - this also indicates that
        // the lines are parallel.
        // If n_a and n_b were both equal to zero the lines would be on top of each
        // other (coincidental).  This check is not done because it is not
        // necessary for this implementation (the parallel check accounts for this).
        if (d == 0)
            return Optional.empty();

        // Calculate the intermediate fractional point that the lines potentially intersect.
        double ua = n_a / d;
        double ub = n_b / d;

        // The fractional point will be between 0 and 1 inclusive if the lines
        // intersect.  If the fractional calculation is larger than 1 or smaller
        // than 0 the lines would need to be longer to intersect.

        if (ua >= 0d && ua <= 1d && ub >= 0d && ub <= 1d) {
            Point ptIntersection = new Point();
            ptIntersection.setX((long) (L1.getStart().getX() + (ua * (L1.getEnd().getX() - L1.getStart().getX()))));
            ptIntersection.setY((long) (L1.getStart().getY() + (ua * (L1.getEnd().getY() - L1.getStart().getY()))));
            return Optional.of(ptIntersection);
        }
        return Optional.empty();
    }

    public Optional<Point> findIntersection(Line l2) {
        return findIntersection(this, l2);
    }

}
