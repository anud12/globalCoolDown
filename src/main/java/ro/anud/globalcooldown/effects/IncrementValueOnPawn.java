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
import ro.anud.globalcooldown.mapper.IncrementValueOnPawnMapper;

import java.util.Objects;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.ADDITION;

@Getter
@ToString
@EqualsAndHashCode
public class IncrementValueOnPawn implements EffectOnPawn {
    public static final String NAME = "INCREMENT_VALUE";
    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementValueOnPawn.class);

    private Long id;
    protected Pawn pawn;
    private int duration;
    private boolean completed;
    private ActionOnPawn actionOnPawn;

    @Builder
    private IncrementValueOnPawn(Long id, Pawn pawn, int duration, ActionOnPawn actionOnPawn) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.pawn = Objects.requireNonNull(pawn, "pawn must not be null");
        this.duration = Objects.requireNonNull(duration, "duration must not be null");
        this.actionOnPawn = Objects.requireNonNull(actionOnPawn, "actionOnPawn must not be null");
        this.completed = false;
    }

    @Override
    public Pawn execute() {
        LOGGER.debug(
                "EXECUTING " +
                        "id=" + id +
                        ", group=" + actionOnPawn.getId() +
                        ", depth=" + actionOnPawn.getDepth());
        duration--;
        if (duration <= 0) {
            completed = true;
        }
        return pawn.streamSetValue(pawn.getValue() + 1);
    }

    @Override
    public boolean isArrived() {
        return completed;
    }

    @Override
    public EffectOnPawnPriority getPriority() {
        return ADDITION;
    }

    @Override
    public EffectOnPawnEntity toEntity() {
        return IncrementValueOnPawnMapper.toEntity(this);
    }

    @Override
    public boolean isExecutable() {
        return actionOnPawn.getDepth() == 0;
    }

    @Override
    public Long getId() {
        return id;
    }
}
