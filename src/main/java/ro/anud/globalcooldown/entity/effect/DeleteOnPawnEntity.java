package ro.anud.globalcooldown.entity.effect;

import lombok.*;
import ro.anud.globalcooldown.effects.DeleteOnPawn;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "delete_on_pawn")
@Data
@NoArgsConstructor

@ToString(callSuper = true)
public class DeleteOnPawnEntity extends EffectOnPawnEntity {

    @Builder
    private DeleteOnPawnEntity(final Long id,
                               final Pawn pawn,
                               final String type,
                               final ActionOnPawnEntity action,
                               final Integer age,
                               final Boolean isSideEffect) {
        this.id = id;
        this.pawn = Objects.requireNonNull(pawn, "pawn must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.action = Objects.requireNonNull(action, "action must not be null");
        this.age = Objects.requireNonNull(age, "age must not be null");
        this.isSideEffect = Objects.requireNonNull(isSideEffect, "isSideEffect must not be null");
    }

    @Override
    public EffectOnPawn toAction() {
        return new DeleteOnPawn(this);
    }
}
