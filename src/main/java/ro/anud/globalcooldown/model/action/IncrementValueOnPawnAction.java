package ro.anud.globalcooldown.model.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.effects.IncrementValueOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.effect.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.effect.IncrementValueOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class IncrementValueOnPawnAction implements ActionOnPawn {

    public static final String NAME = "INCREMENT_VALUE";
    protected Long pawnId;
    private int duration;
    private int rate;

    @Override
    public ActionOnPawnEntity toEntity() {
        Set<EffectOnPawnEntity> effectOnPawnEntityArrayList = new HashSet<>();
        ActionOnPawnEntity actionOnPawnEntity = ActionOnPawnEntity.builder()
                .name(IncrementValueOnPawn.NAME)
                .effectOnPawnEntityList(effectOnPawnEntityArrayList)
                .pawnId(pawnId)
                .build();

        effectOnPawnEntityArrayList.add(IncrementValueOnPawnEntity
                                                .builder()
                                                .action(actionOnPawnEntity)
                                                .pawn(Pawn.builder()
                                                              .id(this.getPawnId())
                                                              .build())
                                                .type(IncrementValueOnPawn.NAME)
                                                .duration(this.getDuration())
                                                .rate(this.rate)
                                                .age(0)
                                                .isSideEffect(false)
                                                .build()
        );
        return actionOnPawnEntity;
    }
}
