package ro.anud.globalcooldown.entity.effect;

import lombok.*;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.effects.MoveOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;

import javax.persistence.Entity;
import javax.persistence.Table;
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
                             final ActionOnPawnEntity action,
                             final Integer age,
                             final Boolean isSideEffect) {
        this.id = id;
        this.pawn = Objects.requireNonNull(pawn, "pawn must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.x = Objects.requireNonNull(x, "x must not be null");
        this.y = Objects.requireNonNull(y, "y must not be null");
        this.age = Objects.requireNonNull(age, "age must not be null");
        this.action = Objects.requireNonNull(action, "action must not be null");
        this.isSideEffect = Objects.requireNonNull(isSideEffect, "isSideEffect must not be null");
    }

    @Override
    public EffectOnPawn toAction() {
        return MoveOnPawn.builder()
                .moveOnPawnEntity(this)
                .build();
    }
}
