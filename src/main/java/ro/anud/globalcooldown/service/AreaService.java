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

        int offset = 5000;
        int size = 70000 + offset;

        List<Line> lines = new ArrayList<>();
        lines.add(Line.builder().start(new Point(offset, offset)).end(new Point(size, offset)).build());
        lines.add(Line.builder().start(new Point(size, offset)).end(new Point(size, size)).build());
        lines.add(Line.builder().start(new Point(size, size)).end(new Point(offset, size)).build());
        lines.add(Line.builder().start(new Point(offset, size)).end(new Point(offset, offset)).build());


        return Area.builder()
                .lineList(lines)
                .build();
    }
}
