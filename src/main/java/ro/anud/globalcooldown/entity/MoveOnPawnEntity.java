package ro.anud.globalcooldown.entity;

import lombok.*;
import ro.anud.globalcooldown.condition.ConditionOnPawnEntity;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.effects.MoveOnPawn;
import ro.anud.globalcooldown.geometry.Point;
import ro.anud.globalcooldown.mapper.ActionOnPawnMapper;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "move_on_pawn")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class MoveOnPawnEntity extends EffectOnPawnEntity {

    private Long x;
    private Long y;

    @Builder
    private MoveOnPawnEntity(final Long id,
                             final Pawn pawn,
                             final String type,
                             final Long x,
                             final Long y,
                             final Integer age,
                             final List<ConditionOnPawnEntity> conditions) {
        this.id = id;
        this.pawn = Objects.requireNonNull(pawn, "pawn must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.x = Objects.requireNonNull(x, "x must not be null");
        this.y = Objects.requireNonNull(y, "y must not be null");
        this.age = Objects.requireNonNull(age, "age must not be null");
        this.conditions = Objects.requireNonNull(conditions, "conditions must not be null");

    }

    @Override
    public EffectOnPawn toAction() {
        return MoveOnPawn.builder()
                .id(this.getId())
                .destination(new Point(this.getX(),
                                       this.getY())
                )
                .pawn(this.getPawn())
                .actionOnPawn(ActionOnPawnMapper.toAction(this.getAction()))
                .age(age)
                .conditions(this.getConditions())
                .build();
    }
}
