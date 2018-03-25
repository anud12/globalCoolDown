package ro.anud.globalcooldown.effects;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalcooldown.action.ActionOnPawn;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.geometry.Point;
import ro.anud.globalcooldown.geometry.Vector;
import ro.anud.globalcooldown.mapper.MoveOnPawnMapper;

import java.util.Objects;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.MOVEMENT;

@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class MoveOnPawn implements EffectOnPawn {
    public static final String NAME = "MOVE_ACTION";
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveOnPawn.class);

    private Long id;
    protected Pawn pawn;
    private boolean arrived;
    private Point destination;
    private ActionOnPawn actionOnPawn;

    @Builder
    public MoveOnPawn(Long id,
                      Pawn pawn,
                      Point destination,
                      ActionOnPawn actionOnPawn) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.pawn = Objects.requireNonNull(pawn, "pawn must not be null");
        this.destination = Objects.requireNonNull(destination, "destination must not be null");
        this.actionOnPawn = Objects.requireNonNull(actionOnPawn, "actionOnPawn must not be null");
        arrived = false;
    }

    @Override
    public Pawn execute() {
        LOGGER.info("for " + pawn.toString());
        arrived = pawn.getPoint().distance(destination) < pawn.getSpeed();
        if (arrived) {
            return pawn.streamSetPoint(destination);
        }
        return pawn.streamSetPoint(calculateNewPosition());
    }

    @Override
    public boolean isArrived() {
        return arrived;
    }

    @Override
    public EffectOnPawnPriority getPriority() {
        return MOVEMENT;
    }

    @Override
    public EffectOnPawnEntity toEntity() {
        return MoveOnPawnMapper.toEntity(this);
    }

    @Override
    public boolean isExecutable() {
        return actionOnPawn.getDepth() == 0;
    }

    @Override
    public Long getId() {
        return id;
    }

    private Point calculateNewPosition() {
        Vector normalized = Vector.normalized(destination.duplicate()
                                                      .streamSubstract(pawn.getPoint()));
        return pawn.getPoint()
                .duplicate()
                .streamTranspose(normalized, pawn.getSpeed());
    }
}
