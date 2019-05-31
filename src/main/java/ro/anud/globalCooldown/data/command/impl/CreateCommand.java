package ro.anud.globalCooldown.data.command.impl;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.data.trait.OwnerTrait;
import ro.anud.globalCooldown.data.command.Command;
import ro.anud.globalCooldown.data.command.CommandPlan;
import ro.anud.globalCooldown.data.command.CommandScope;
import ro.anud.globalCooldown.data.command.CommandValidator;

import java.util.Objects;

import static ro.anud.globalCooldown.data.command.CommandPlan.end;
import static ro.anud.globalCooldown.data.command.CommandPlan.untargetedInstruction;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateCommand implements Command {
    public static CommandValidator commandValidator = (gameObjectModel, commandScope) ->
            !commandScope.getOptionalValidation()
                    .createChain()
                    .validate(gameObjectModel.getTrait(LocationTrait.class))
                    .validate(gameObjectModel.getTrait(OwnerTrait.class))
                    .isAnyNotPresent();

    private final GameObjectModel newGameObjectModel;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCommand.class);

    @Builder
    public CreateCommand(final GameObjectModel newGameObjectModel) {
        this.newGameObjectModel = Objects.requireNonNull(newGameObjectModel, "newGameObjectModel must not be null");
    }

    @Override
    public CommandPlan plan(final GameObjectModel gameObjectModel, final CommandScope commandScope) {
        if(!commandValidator.validate(gameObjectModel, commandScope)){
            return end();
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
        return untargetedInstruction(() -> commandScope.getGameObjectRepository().insert(newGameObjectModel));
    }
}
