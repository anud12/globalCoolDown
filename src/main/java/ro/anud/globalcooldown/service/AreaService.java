package ro.anud.globalcooldown.service;

import org.springframework.stereotype.Service;
import ro.anud.globalcooldown.geometry.Area;
import ro.anud.globalcooldown.geometry.Line;
import ro.anud.globalcooldown.geometry.Point;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaService {

    public Area getTotalArea() {
        List<Line> lines = new ArrayList<>();
        lines.add(Line.builder().start(new Point(10000, 10000)).end(new Point(50000, 1000)).build());
        lines.add(Line.builder().start(new Point(50000, 1000)).end(new Point(25000, 25000)).build());
        lines.add(Line.builder().start(new Point(25000, 25000)).end(new Point(50000, 40000)).build());
        lines.add(Line.builder().start(new Point(50000, 40000)).end(new Point(50000, 50000)).build());
        lines.add(Line.builder().start(new Point(50000, 50000)).end(new Point(1000, 50000)).build());
        lines.add(Line.builder().start(new Point(1000, 50000)).end(new Point(10000, 10000)).build());


        return Area.builder()
                .lineList(lines)
                .build();
    }
}
