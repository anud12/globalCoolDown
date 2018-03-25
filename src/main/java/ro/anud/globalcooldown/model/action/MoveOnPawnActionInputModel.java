package ro.anud.globalcooldown.model.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.effects.MoveOnPawn;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.EffectOnPawnEntity;
import ro.anud.globalcooldown.entity.MoveOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;

import java.util.ArrayList;

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
        ArrayList<EffectOnPawnEntity> effectOnPawnEntityArrayList = new ArrayList<>();
        ActionOnPawnEntity actionOnPawnEntity = ActionOnPawnEntity.builder()
                .name(MoveOnPawnActionInputModel.NAME)
                .effectOnPawnEntityList(effectOnPawnEntityArrayList)
                .pawnId(pawnId)
                .build();

        effectOnPawnEntityArrayList.add(MoveOnPawnEntity.builder()
                                                .pawn(Pawn.builder()
                                                              .id(this.getPawnId())
                                                              .build())
                                                .x(this.getX())
                                                .y(this.getY())
                                                .type(MoveOnPawn.NAME)
                                                .build()
        );
        return actionOnPawnEntity;
    }
}
