package ro.anud.globalcooldown.entity;

import lombok.*;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.effects.IncrementValueOnPawn;
import ro.anud.globalcooldown.mapper.ActionOnPawnMapper;

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
                                       final ActionOnPawnEntity actionOnPawnEntity,
                                       final Integer age) {
        this.id = id;
        this.pawn = pawn;
        this.type = type;
        this.duration = duration;
        this.rate = rate;
        this.action = actionOnPawnEntity;
        this.age = Objects.requireNonNull(age, "age must not be null");
    }

    @Override
    public EffectOnPawn toAction() {
        return IncrementValueOnPawn.builder()
                .id(this.getId())
                .duration(this.getDuration())
                .rate(this.rate)
                .pawn(this.getPawn())
                .actionOnPawn(ActionOnPawnMapper.toAction(this.getAction()))
                .age(age)
                .build();
    }
}
