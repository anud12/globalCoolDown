package ro.anud.globalcooldown.entity;

import lombok.*;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.effects.IncrementValueOnPawn;
import ro.anud.globalcooldown.mapper.ActionOnPawnMapper;
import ro.anud.globalcooldown.service.AreaService;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "increment_value_on_pawn")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class IncrementValueOnPawnEntity extends EffectOnPawnEntity {
    private int duration;
    private int rate;

    @Builder
    private IncrementValueOnPawnEntity(final Long id,
                                       final Pawn pawn,
                                       final String type,
                                       final int duration,
                                       final Integer rate,
                                       final ActionOnPawnEntity action,
                                       final Integer age,
                                       final Boolean isSideEffect) {
        this.id = id;
        this.pawn = Objects.requireNonNull(pawn, "pawn must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.duration = Objects.requireNonNull(duration, "duration must not be null");
        this.rate = Objects.requireNonNull(rate, "rate must not be null");
        this.action = Objects.requireNonNull(action, "action must not be null");
        this.age = Objects.requireNonNull(age, "age must not be null");
        this.isSideEffect = Objects.requireNonNull(isSideEffect, "isSideEffect must not be null");
    }

    @Override
    public EffectOnPawn toAction(AreaService areaService) {
        return IncrementValueOnPawn.builder()
                .id(this.getId())
                .duration(this.getDuration())
                .rate(this.rate)
                .pawn(this.getPawn())
                .actionOnPawn(ActionOnPawnMapper.toAction(this.getAction()))
                .age(age)
                .isSideEffect(isSideEffect)
                .build();
    }
}
