package ro.anud.globalcooldown.effects;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalcooldown.entity.effect.ConditionOnPawnEntity;
import ro.anud.globalcooldown.entity.effect.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.effect.IncrementValueOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.service.GameDataService;

import java.util.Objects;

import static ro.anud.globalcooldown.effects.EffectOnPawnPriority.ADDITION;

@Getter
@ToString
@EqualsAndHashCode
public class IncrementValueOnPawn implements EffectOnPawn {
    public static final String NAME = "INCREMENT_VALUE";
    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementValueOnPawn.class);

    private boolean completed;
    private EffectOnPawnPriority priority;

    private IncrementValueOnPawnEntity entity;

    @Builder
    private IncrementValueOnPawn(final IncrementValueOnPawnEntity incrementValueOnPawnEntity) {
        this.entity = Objects.requireNonNull(incrementValueOnPawnEntity, "incrementValueOnPawnEntity must not be null");

        this.priority = EffectOnPawnPriority.SUBTRACTION;
        if (incrementValueOnPawnEntity.getRate() < 0) {
            priority = EffectOnPawnPriority.ADDITION;
        }
        this.completed = false;
    }

    @Override
    public Pawn execute(GameDataService gameDataService) {
        LOGGER.debug(
                "execute " +
                        "id=" + entity.getId() +
                        ", group=" + entity.getAction().getId() +
                        ", depth=" + entity.getAction().getDepth() +
                        ", for " + entity.getPawn());
        entity.setDuration(entity.getDuration() - 1);
        if (entity.getDuration() <= 0) {
            completed = true;
        }
        return entity.getPawn().streamSetValue(entity.getPawn().getValue() + entity.getRate());
    }

    @Override
    public EffectOnPawnEntity toEntity() {
        return IncrementValueOnPawnEntity.builder()
                .id(entity.getId())
                .type(IncrementValueOnPawn.NAME)
                .duration(entity.getDuration())
                .pawn(entity.getPawn())
                .rate(entity.getRate())
                .age(entity.getAge())
                .action(entity.getAction())
                .isSideEffect(entity.getIsSideEffect())
                .build();
    }

    @Override
    public boolean isExecutable() {
        boolean executable;
        executable = entity.getAction().getDepth() == 0;
        for (ConditionOnPawnEntity condition : entity.getAction().getConditions()) {
            if (!condition.test(entity.getPawn())) {
                executable = false;
            }
        }
        LOGGER.debug("isExecutable :" + executable);
        return executable;
    }

    @Override
    public void incrementAge() {
        entity.setAge(entity.getAge() + 1);
        LOGGER.debug("incrementAge :" + entity.getAge());
    }

    @Override
    public void resetAge() {
        entity.setAge(0);
        LOGGER.debug("resetAge :" + entity.getAge());
    }

    @Override
    public Integer getAge() {
        return entity.getAge();
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
