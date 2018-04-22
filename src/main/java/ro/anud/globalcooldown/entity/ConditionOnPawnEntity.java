package ro.anud.globalcooldown.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.anud.globalcooldown.comparator.LongComparator;
import ro.anud.globalcooldown.action.PawnAttributeExtractorLong;

import javax.persistence.*;

@Entity
@Table(name = "condition_on_pawn")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionOnPawnEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_condition_on_pawn_gen")
    @SequenceGenerator(name = "seq_condition_on_pawn_gen", allocationSize = 1, sequenceName = "seq_condition_on_pawn")
    private Long id;
    private PawnAttributeExtractorLong attribute;
    private LongComparator comparator;
    private Long value;

    public boolean test(Pawn pawn) {
        return comparator
                .compare(attribute.apply(pawn))
                .test(value);
    }
}
