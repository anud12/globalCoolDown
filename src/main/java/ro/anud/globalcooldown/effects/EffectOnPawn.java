package ro.anud.globalcooldown.effects;

import org.slf4j.Logger;
import ro.anud.globalcooldown.action.ActionOnPawn;
import ro.anud.globalcooldown.condition.ConditionOnPawnEntity;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;

import java.util.Objects;

public abstract class EffectOnPawn {

    private final Logger logger;

    protected Long id;
    protected Pawn pawn;
    protected ActionOnPawn actionOnPawn;
    protected Integer age;
    protected Boolean isSideEffect;

    public EffectOnPawn(Long id,
                        Pawn pawn,
                        ActionOnPawn actionOnPawn,
                        Integer age,
                        Boolean isSideEffect,
                        Logger logger) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.pawn = Objects.requireNonNull(pawn, "pawn must not be null");
        this.actionOnPawn = Objects.requireNonNull(actionOnPawn, "actionOnPawn must not be null");
        this.age = Objects.requireNonNull(age, "age must not be null");
        this.isSideEffect = Objects.requireNonNull(isSideEffect, "isSideEffect must not be null");
        this.logger = Objects.requireNonNull(logger, "logger must not be null");
    }

    public abstract Pawn execute();

    public abstract boolean isRemovable();

    public abstract EffectOnPawnPriority getPriority();

    public abstract EffectOnPawnEntity toEntity();

    public boolean isExecutable() {
        boolean executable;
        executable = actionOnPawn.getDepth() == 0;
        for (ConditionOnPawnEntity condition : actionOnPawn.getConditionOnPawnEntitySet()) {
            if (!condition.test(pawn)) {
                executable = false;
            }
        }

        logger.debug("isExecutable :" + executable);
        return executable;
    }

    public void incrementAge() {
        this.age += 1;
        logger.debug("incrementAge :" + this.age);
    }

    public void resetAge() {
        this.age = 0;
        logger.debug("resetAge :" + this.age);
    }

    public Integer getAge() {
        return this.age;
    }

    public Long getId() {
        return id;
    }
}
