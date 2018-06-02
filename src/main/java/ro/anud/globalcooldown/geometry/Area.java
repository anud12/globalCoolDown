package ro.anud.globalcooldown.geometry;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Data
public class Area {
    private List<Line> lineList;

    private static Logger LOGGER = LoggerFactory.getLogger(Area.class);
    private boolean walkable;

    public Area(List<Line> lineList,
                boolean walkable) {
        Objects.requireNonNull(lineList, "lineList must not be null");
        this.walkable = Objects.requireNonNull(walkable, "walkable must not be null");

        this.lineList = new ArrayList<>();
        final Line[] compareLine = {null};
        lineList.stream().flatMap(line -> Stream.empty());
        lineList.forEach(line -> {
            if (Objects.isNull(compareLine[0])) {
                compareLine[0] = line;
                this.lineList.add(line);
            }
            lineList.stream()
                    .filter(line1 -> compareLine[0].getEnd().equals(line1.getStart()))
                    .findFirst()
                    .ifPresent(line1 -> {
                        compareLine[0] = line1;
                        this.lineList.add(line1);
                    });
        });

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
