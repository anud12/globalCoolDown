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
import ro.anud.globalcooldown.entity.IncrementValueOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;

import java.util.Objects;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.ADDITION;

@Getter
@ToString
@EqualsAndHashCode
public class IncrementValueOnPawn extends EffectOnPawn {
    public static final String NAME = "INCREMENT_VALUE";
    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementValueOnPawn.class);

    private int duration;
    private int rate;
    private boolean completed;
    private EffectOnPawnPriority priority;

    @Builder
    private IncrementValueOnPawn(final Long id,
                                 final Pawn pawn,
                                 final ActionOnPawn actionOnPawn,
                                 final Integer age,
                                 final Boolean isSideEffect,
                                 final int duration,
                                 final Integer rate) {
        super(id, pawn, actionOnPawn, age, isSideEffect, LOGGER);
        this.duration = Objects.requireNonNull(duration, "duration must not be null");
        this.rate = Objects.requireNonNull(rate, "rate must not be null");
        this.priority = EffectOnPawnPriority.SUBTRACTION;
        if(rate < 0) {
            priority = EffectOnPawnPriority.ADDITION;
        }
        this.completed = false;
    }

    @Override
    public Pawn execute() {
        LOGGER.debug(
                "execute " +
                        "id=" + id +
                        ", group=" + actionOnPawn.getId() +
                        ", depth=" + actionOnPawn.getDepth() +
                        ", for " + pawn);
        duration--;
        if (duration <= 0) {
            completed = true;
        }
        return pawn.streamSetValue(pawn.getValue() + this.rate);
    }

    @Override
    public EffectOnPawnEntity toEntity() {
        return IncrementValueOnPawnEntity.builder()
                .id(this.getId())
                .type(IncrementValueOnPawn.NAME)
                .duration(this.getDuration())
                .pawn(this.pawn)
                .rate(this.getRate())
                .age(this.getAge())
                .action(ActionOnPawnEntity.builder()
                                .id(this.actionOnPawn.getId())
                                .build())
                .isSideEffect(isSideEffect)
                .build();
    }

    @Override
    public boolean isRemovable() {
        LOGGER.debug("isRemovable :" + completed);
        return completed;
    }

    @Override
    public EffectOnPawnPriority getPriority() {

        LOGGER.debug("getPriority :" + ADDITION);
        return ADDITION;
    }


}
