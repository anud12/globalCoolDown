package ro.anud.globalCooldown.engine.command.type;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.data.trait.OwnerTrait;
import ro.anud.globalCooldown.engine.command.Command;
import ro.anud.globalCooldown.engine.command.CommandResponse;
import ro.anud.globalCooldown.engine.command.CommandScope;

import java.util.Arrays;
import java.util.Objects;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class CreateCommand implements Command {

    private final GameObjectModel newGameObjectModel;

    public CreateCommand(final GameObjectModel newGameObjectModel) {
        this.newGameObjectModel = Objects.requireNonNull(newGameObjectModel, "newGameObjectModel must not be null");
    }

    @Override
    public CommandResponse execute(final CommandScope commandScope,
                                   final GameObjectModel gameObjectModel) {
        if (commandScope.getOptionalValidation().createChain()
                .validate(gameObjectModel.getTrait(LocationTrait.class))
                .validate(gameObjectModel.getTrait(OwnerTrait.class))
                .isAnyNotPresent()) {
            return CommandResponse.builder()
                    .nextCommand(null)
                    .build();
        }
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
