package ro.anud.globalcooldown.model.pawn;

import lombok.Builder;
import lombok.Data;
import ro.anud.globalcooldown.geometry.Point;

@Data
@Builder
public class PawnOutputModel {
    private Long id;
    private Integer value;
    private Long version;
    private Point point;
    private Long speed;
    private Long characterCode;
}
