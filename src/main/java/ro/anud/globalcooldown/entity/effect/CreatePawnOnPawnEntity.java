package ro.anud.globalcooldown.entity.effect;

import lombok.*;
import ro.anud.globalcooldown.effects.CreatePawnOnPawn;
import ro.anud.globalcooldown.effects.EffectOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.generator.PawnGenerator;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "create_pawn_on_pawn")
@Data
@AllArgsConstructor
@NoArgsConstructor

@ToString(callSuper = true)
public class CreatePawnOnPawnEntity extends EffectOnPawnEntity {

    private PawnGenerator pawnGenerator;

    @Builder
    private CreatePawnOnPawnEntity(final Long id,
                                   final Pawn pawn,
                                   final String type,
                                   final ActionOnPawnEntity action,
                                   final Integer age,
                                   final Boolean isSideEffect,
                                   final PawnGenerator pawnGenerator) {
        this.id = id;
        this.pawn = Objects.requireNonNull(pawn, "pawn must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.action = Objects.requireNonNull(action, "action must not be null");
        this.age = Objects.requireNonNull(age, "age must not be null");
        this.isSideEffect = Objects.requireNonNull(isSideEffect, "isSideEffect must not be null");
        this.pawnGenerator = Objects.requireNonNull(pawnGenerator, "pawnGenerator must not be null");
    }

    @Override
    public EffectOnPawn toAction() {
        return new CreatePawnOnPawn(this);
    }
}
