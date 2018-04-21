package ro.anud.globalcooldown.model.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.condition.ConditionOnPawnEntity;
import ro.anud.globalcooldown.condition.NumberAttributeComparatorUtil;
import ro.anud.globalcooldown.condition.PawnLongAttributeExtractor;
import ro.anud.globalcooldown.effects.IncrementValueOnPawn;
import ro.anud.globalcooldown.effects.MoveOnPawn;
import ro.anud.globalcooldown.entity.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MoveOnPawnActionInputModel implements ActionOnPawnInputModel {
    public static final String NAME = "MOVE_ACTION";
    private Long pawnId;
    private Long x;
    private Long y;

    @Override
    public ActionOnPawnEntity toEntity() {
        Pawn pawn = Pawn.builder()
                .id(this.getPawnId())
                .build();

        Set<EffectOnPawnEntity> effectOnPawnEntityArrayList = new HashSet<>();
        ActionOnPawnEntity actionOnPawnEntity = ActionOnPawnEntity.builder()
                .name(MoveOnPawnActionInputModel.NAME)
                .effectOnPawnEntityList(effectOnPawnEntityArrayList)
                .pawnId(pawnId)
                .conditions(Collections.singleton(ConditionOnPawnEntity.builder()
                                                          .attribute(
                                                                  PawnLongAttributeExtractor.VALUE)
                                                          .comparator(
                                                                  NumberAttributeComparatorUtil.GREATER_THAN)
                                                          .value(0L)
                                                          .build()))
                .build();

        effectOnPawnEntityArrayList.add(MoveOnPawnEntity.builder()
                                                .action(actionOnPawnEntity)
                                                .pawn(pawn)
                                                .type(MoveOnPawn.NAME)
                                                .age(0)
                                                .isSideEffect(false)
                                                .x(this.getX())
                                                .y(this.getY())
                                                .build());
        effectOnPawnEntityArrayList.add(IncrementValueOnPawnEntity.builder()
                                                .action(actionOnPawnEntity)
                                                .pawn(pawn)
                                                .type(IncrementValueOnPawn.NAME)
                                                .age(0)
                                                .isSideEffect(true)
                                                .duration(Integer.MAX_VALUE)
                                                .rate(-1)
                                                .build());
        return actionOnPawnEntity;
    }
}
