package ro.anud.globalcooldown.effects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.entity.effect.DeleteOnPawnEntity;
import ro.anud.globalcooldown.entity.effect.EffectOnPawnEntity;
import ro.anud.globalcooldown.service.GameDataService;

import java.util.Objects;

public class DeleteOnPawn implements EffectOnPawn {
    public static final String NAME = "CREATE_PAWN";
    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementValueOnPawn.class);

    private DeleteOnPawnEntity entity;

    public DeleteOnPawn(final DeleteOnPawnEntity entity) {
        this.entity = Objects.requireNonNull(entity,
                                             "entity must not be null");
    }

    @Override
    public Pawn execute(GameDataService gameDataService) {
        Pawn parentPawn = entity.getPawn();
        gameDataService.getPawnService().remove(parentPawn);
        return new Pawn();
    }

    @Override
    public boolean isRemovable() {
        return true;
    }

    @Override
    public EffectOnPawnPriority getPriority() {
        return EffectOnPawnPriority.CREATION;
    }

    @Override
    public EffectOnPawnEntity toEntity() {
        return DeleteOnPawnEntity.builder()
                .id(entity.getId())
                .type(DeleteOnPawn.NAME)
                .pawn(entity.getPawn())
                .age(entity.getAge())
                .action(entity.getAction())
                .isSideEffect(entity.getIsSideEffect())
                .build();
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public void incrementAge() {
        entity.setAge(entity.getAge() + 1);
        LOGGER.debug("incrementAge :" + entity.getAge());
    }

    @Override
    public void resetAge() {

    }

    @Override
    public Integer getAge() {
        return null;
    }
}
