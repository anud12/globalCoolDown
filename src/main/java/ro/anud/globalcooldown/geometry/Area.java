package ro.anud.globalcooldown.geometry;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Data
public class Area {
    private List<Line> lineList;

    private static Logger LOGGER = LoggerFactory.getLogger(Area.class);

    public Area(List<Line> lineList) {
        this.lineList = Objects.requireNonNull(lineList, "lineList must not be null");

    }


    public Optional<List<Point>> getIntersection(Line argument) {
        List<Point> points = lineList.stream()
                .map(line -> line.findIntersection(argument))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        if (points.isEmpty())
            return Optional.empty();
        return Optional.of(points);
    }
}
