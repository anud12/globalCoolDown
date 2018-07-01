package ro.anud.globalcooldown.model.action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.entity.effect.*;
import ro.anud.globalcooldown.generator.PawnGenerator;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CreatePawnfromPawnAction implements ActionOnPawn {
    public static final String NAME = "CREATE_PAWN_ACTION";
    private Long pawnId;

    @Override
    public ActionOnPawnEntity toEntity() {
        Pawn pawn = Pawn.builder()
                .id(this.getPawnId())
                .build();

        Set<EffectOnPawnEntity> effectOnPawnEntityArrayList = new HashSet<>();
        ActionOnPawnEntity actionOnPawnEntity = ActionOnPawnEntity.builder()
                .name(NAME)
                .effectOnPawnEntityList(effectOnPawnEntityArrayList)
                .pawnId(pawnId)
                .build();

        effectOnPawnEntityArrayList.add(CreatePawnOnPawnEntity.builder()
                                                .action(actionOnPawnEntity)
                                                .pawn(pawn)
                                                .type(NAME)
                                                .age(0)
                                                .isSideEffect(false)
                                                .pawnGenerator(PawnGenerator.PAWN)
                                                .build());
        return actionOnPawnEntity;
    }
}
