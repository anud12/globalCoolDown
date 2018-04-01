package ro.anud.globalcooldown.mapper;

import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.model.pawn.PawnOutputModel;

public class PawnMapper {
    private PawnMapper() {
    }

    public static PawnOutputModel toPawnOutputModel(Pawn pawn) {
        return PawnOutputModel.builder()
                .id(pawn.getId())
                .name(pawn.getName())
                .point(pawn.getPoint())
                .speed(pawn.getSpeed())
                .version(pawn.getVersion())
                .value(pawn.getValue())
                .characterCode(35L)
                .build();
    }
}
