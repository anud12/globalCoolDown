package ro.anud.globalcooldown.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.anud.globalcooldown.entity.effect.ConditionOnPawnEntity;
import ro.anud.globalcooldown.entity.effect.EffectOnPawnEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "action_on_pawn")
public class ActionOnPawnEntity implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_action_on_pawn_gen")
    @SequenceGenerator(name = "seq_action_on_pawn_gen", allocationSize = 1, sequenceName = "seq_action_on_pawn")
    private Long id;
    private String name;

    private long pawnId;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "action_id")
    private Set<EffectOnPawnEntity> effectOnPawnEntityList;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id")
    private Set<ConditionOnPawnEntity> conditions;


    private String saveDateTime;

    @OneToOne
    private ActionOnPawnEntity parent;

    @Override
    public String toString() {
        return "ActionOnPawnEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                '}';
    }

    public Integer getDepth() {
        int depth = -1;
        ActionOnPawnEntity action = this;
        do {
            depth++;
            action = action.getParent();
        } while (Objects.nonNull(action));
        return depth;
    }
}
