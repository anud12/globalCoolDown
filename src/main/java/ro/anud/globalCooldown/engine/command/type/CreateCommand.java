package ro.anud.globalCooldown.engine.command.type;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.data.trait.ManufacturingTrait;
import ro.anud.globalCooldown.data.trait.OwnerTrait;
import ro.anud.globalCooldown.engine.command.Command;
import ro.anud.globalCooldown.engine.command.CommandPreCheckException;
import ro.anud.globalCooldown.engine.command.CommandResponse;
import ro.anud.globalCooldown.engine.command.CommandScope;

import java.util.Arrays;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class CreateCommand implements Command {

    private final GameObjectModel newGameObjectModel;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCommand.class);

    @Builder
    public CreateCommand(final GameObjectModel targetGameObjectModel,
                         final GameObjectModel newGameObjectModel) throws CommandPreCheckException {
        this.newGameObjectModel = Objects.requireNonNull(newGameObjectModel, "newGameObjectModel must not be null");
        LOGGER.info(targetGameObjectModel.getTraitMap().toString());
        if (!targetGameObjectModel.getTrait(ManufacturingTrait.class).isPresent()
                || !targetGameObjectModel.getTrait(LocationTrait.class).isPresent()
                || !targetGameObjectModel.getTrait(OwnerTrait.class).isPresent()) {
            throw new CommandPreCheckException(targetGameObjectModel, "Can't create that!");
        }
    }

    @Override
    public CommandResponse execute(final CommandScope commandScope,
                                   final GameObjectModel gameObjectModel) {
        LocationTrait locationTrait = gameObjectModel.getTrait(LocationTrait.class).get();
        OwnerTrait ownerTrait = gameObjectModel.getTrait(OwnerTrait.class).get();
        newGameObjectModel.addTrait(LocationTrait.builder()
                .angle(locationTrait.getAngle())
                .point2D(locationTrait.getPoint2D())
                .build());
        newGameObjectModel.addTrait(OwnerTrait.builder()
                .ownerId(ownerTrait.getOwnerId())
                .build());
        return CommandResponse.builder()
                .triggerList(Arrays.asList(commandScope
                        .getTriggerFactory()
                        .createGameObjectTrigger(newGameObjectModel)))
                .nextCommand(null)
                .build();
    }

}
