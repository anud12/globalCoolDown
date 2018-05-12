package ro.anud.globalcooldown.effects;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalcooldown.action.ActionOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.MoveOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.geometry.Line;
import ro.anud.globalcooldown.geometry.Point;
import ro.anud.globalcooldown.geometry.Vector;
import ro.anud.globalcooldown.service.AreaService;

import java.util.Objects;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.MOVEMENT;

@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class MoveOnPawn extends EffectOnPawn {
    public static final String NAME = "MOVE_ACTION";
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveOnPawn.class);
    private static Long COLLISSION_REVERT_DISTANCE = 10L;

    private boolean arrived;
    private Point destination;
    private AreaService areaService;

    @Builder
    public MoveOnPawn(Long id,
                      Pawn pawn,
                      Point destination,
                      ActionOnPawn actionOnPawn,
                      Integer age,
                      Boolean isSideEffect,
                      AreaService areaService) {
        super(id, pawn, actionOnPawn, age, isSideEffect, LOGGER);
        this.destination = Objects.requireNonNull(destination, "destination must not be null");
        this.areaService = Objects.requireNonNull(areaService, "areaService must not be null");
        arrived = false;
    }

    @Override
    public Pawn execute() {
        LOGGER.debug("execute " +
                             "id=" + id +
                             ", group=" + actionOnPawn.getId() +
                             ", depth=" + actionOnPawn.getDepth() +
                             ", for " + pawn);
        Point starPoint = pawn.getPoint();
        arrived = pawn.getPoint().distance(destination) < pawn.getSpeed();
        if (arrived) {
            pawn.setPoint(areaService.getTotalArea()
                                  .getIntersection(Line.builder().start(starPoint)
                                                           .end(destination)
                                                           .build())
                                  .map(points -> points.stream()
                                          .sorted((o1, o2) -> (int) (o1.distance(destination) - o2.distance(
                                                  destination)))
                                          .findFirst()
                                          .orElse(destination))
                                  .orElse(destination));
            return pawn;
        }
        Vector normalized = Vector.normalized(destination.duplicate()
                                                      .streamSubstract(pawn.getPoint()));

        Point newPoint = pawn.getPoint()
                .duplicate()
                .streamTranspose(normalized, pawn.getSpeed() + COLLISSION_REVERT_DISTANCE);
        pawn.setPoint(areaService.getTotalArea()
                              .getIntersection(Line.builder()
                                                       .start(starPoint)
                                                       .end(newPoint)
                                                       .build())
                              .map(points -> points.stream()
                                      .sorted((o1, o2) -> (int) (o1.distance(newPoint) - o2.distance(newPoint)))
                                      .findFirst()
                                      .map(point -> {
                                          arrived = true;
                                          return point.streamTranspose(normalized, -COLLISSION_REVERT_DISTANCE * 2);
                                      })
                                      .orElse(newPoint))
                              .orElse(newPoint));
        return pawn;
    }

    @Override
    public EffectOnPawnEntity toEntity() {
        return MoveOnPawnEntity.builder()
                .id(this.getId())
                .pawn(this.pawn)
                .type(MoveOnPawn.NAME)
                .x(this.getDestination().getX())
                .y(this.getDestination().getY())
                .age(this.age)
                .action(ActionOnPawnEntity.builder()
                                .id(this.actionOnPawn.getId())
                                .build())
                .isSideEffect(isSideEffect)
                .build();
    }

    @Override
    public boolean isRemovable() {
        LOGGER.debug("isRemovable :" + arrived);
        return arrived;
    }

    @Override
    public EffectOnPawnPriority getPriority() {
        LOGGER.debug("getPriority :" + MOVEMENT);
        return MOVEMENT;
    }
}
