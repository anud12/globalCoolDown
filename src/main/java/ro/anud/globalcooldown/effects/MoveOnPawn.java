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
import ro.anud.globalcooldown.geometry.Point;
import ro.anud.globalcooldown.geometry.Vector;

import java.util.Objects;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.MOVEMENT;

@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class MoveOnPawn extends EffectOnPawn {
    public static final String NAME = "MOVE_ACTION";
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveOnPawn.class);


    private boolean arrived;
    private Point destination;

    @Builder
    public MoveOnPawn(Long id,
                      Pawn pawn,
                      Point destination,
                      ActionOnPawn actionOnPawn,
                      Integer age,
                      Boolean isSideEffect) {
        super(id, pawn, actionOnPawn, age, isSideEffect, LOGGER);
        this.destination = Objects.requireNonNull(destination, "destination must not be null");
        arrived = false;
    }

    @Override
    public Pawn execute() {
        LOGGER.debug("execute " +
                             "id=" + id +
                             ", group=" + actionOnPawn.getId() +
                             ", depth=" + actionOnPawn.getDepth() +
                             ", for " + pawn);
        arrived = pawn.getPoint().distance(destination) < pawn.getSpeed();
        if (arrived) {
            return pawn.streamSetPoint(destination);
        }
        Vector normalized = Vector.normalized(destination.duplicate()
                                                      .streamSubstract(pawn.getPoint()));
        return pawn.streamSetPoint(pawn.getPoint()
                                           .duplicate()
                                           .streamTranspose(normalized, pawn.getSpeed()));
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
