package ro.anud.globalcooldown.effects;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalcooldown.entity.*;
import ro.anud.globalcooldown.geometry.Line;
import ro.anud.globalcooldown.geometry.Point;
import ro.anud.globalcooldown.geometry.Vector;
import ro.anud.globalcooldown.service.AreaService;
import ro.anud.globalcooldown.service.GameDataService;

import java.util.Objects;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.MOVEMENT;

@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class MoveOnPawn implements EffectOnPawn {
    public static final String NAME = "MOVE_ACTION";
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveOnPawn.class);
    private static Long COLLISSION_REVERT_DISTANCE = 10L;

    private boolean arrived;

    private final Long id;
    private final Pawn pawn;
    private final ActionOnPawnEntity actionOnPawn;
    private final Boolean isSideEffect;

    private final Point destination;
    private Integer age;

    @Builder
    public MoveOnPawn(final MoveOnPawnEntity moveOnPawnEntity) {
        Objects.requireNonNull(moveOnPawnEntity, "moveOnPawnEntity must not be null");
        this.id = moveOnPawnEntity.getId();
        this.pawn = moveOnPawnEntity.getPawn();
        this.actionOnPawn = moveOnPawnEntity.getAction();
        this.age = moveOnPawnEntity.getAge();
        this.isSideEffect = moveOnPawnEntity.getIsSideEffect();
        this.destination = new Point(moveOnPawnEntity.getX(), moveOnPawnEntity.getY());

        arrived = false;
    }

    @Override
    public Pawn execute(final GameDataService gameDataService) {
        AreaService areaService = gameDataService.getAreaService();
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
    public boolean isExecutable() {
        boolean executable;
        executable = actionOnPawn.getDepth() == 0;
        for (ConditionOnPawnEntity condition : actionOnPawn.getConditions()) {
            if (!condition.test(pawn)) {
                executable = false;
            }
        }
        LOGGER.debug("isExecutable :" + executable);
        return executable;
    }

    @Override
    public void incrementAge() {
        this.age += 1;
        LOGGER.debug("incrementAge :" + this.age);
    }

    @Override
    public void resetAge() {
        this.age = 0;
        LOGGER.debug("resetAge :" + this.age);
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
