package ro.anud.globalcooldown.entity;

import lombok.Data;
import lombok.ToString;
import ro.anud.globalcooldown.condition.ConditionOnPawnEntity;
import ro.anud.globalcooldown.effects.EffectOnPawn;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "effect_on_pawn")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@ToString()
public abstract class EffectOnPawnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_effect_on_pawn_gen")
    @SequenceGenerator(name = "seq_effect_on_pawn_gen", allocationSize = 1, sequenceName = "seq_effect_on_pawn")
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "pawn_id")
    protected Pawn pawn;
    protected String type;

    @ManyToOne(fetch = FetchType.EAGER)
    protected ActionOnPawnEntity action;

    protected Integer age;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "effect_on_pawn_id")
    protected List<ConditionOnPawnEntity> conditions;

    public abstract EffectOnPawn toAction();
}
