package ro.anud.globalcooldown.effects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalcooldown.entity.ActionOnPawnEntity;
import ro.anud.globalcooldown.entity.Pawn;
import ro.anud.globalcooldown.entity.effect.CreatePawnOnPawnEntity;
import ro.anud.globalcooldown.entity.effect.EffectOnPawnEntity;
import ro.anud.globalcooldown.service.GameDataService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CreatePawnOnPawn implements EffectOnPawn {
    public static final String NAME = "CREATE_PAWN";
    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementValueOnPawn.class);

    private CreatePawnOnPawnEntity entity;

    public CreatePawnOnPawn(final CreatePawnOnPawnEntity entity) {
        this.entity = Objects.requireNonNull(entity,
                                             "entity must not be null");
    }

    @Override
    public Pawn execute(GameDataService gameDataService) {
        Pawn parentPawn = entity.getPawn();
        Pawn pawn = entity.getPawnGenerator().createPawn();
        pawn.setUserId(parentPawn.getUserId());
        pawn.setPoint(entity.getPawn().getPoint());

        pawn = gameDataService.getPawnService().save(pawn);
        Set<EffectOnPawnEntity> effectOnPawnEntitySet = new HashSet<>();
        ActionOnPawnEntity actionOnPawnEntity = ActionOnPawnEntity.builder()
                .pawnId(pawn.getId())
                .name("CREATING_PAWN")
                .effectOnPawnEntityList(effectOnPawnEntitySet)
                .build();
        actionOnPawnEntity.setEffectOnPawnEntityList(entity.getPawnGenerator()
                                                             .createEffectsForPawn(pawn, entity.getAction()));
        gameDataService
                .getActionService()
                .save(actionOnPawnEntity);
        return pawn;
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
        return CreatePawnOnPawnEntity.builder()
                .id(entity.getId())
                .type(CreatePawnOnPawn.NAME)
                .pawn(entity.getPawn())
                .age(entity.getAge())
                .action(entity.getAction())
                .isSideEffect(entity.getIsSideEffect())
                .pawnGenerator(entity.getPawnGenerator())
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
