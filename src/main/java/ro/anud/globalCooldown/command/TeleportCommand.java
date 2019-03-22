package ro.anud.globalCooldown.command;

import lombok.*;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.LocationTrait;

import java.util.Collections;
import java.util.Optional;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeleportCommand implements Command {

    private double x;
    private double y;

    @Override
    public CommandResponse execute(GameObjectModel gameObjectModel) {
        gameObjectModel.getTrait(LocationTrait.class).ifPresent(trait -> {
            trait.setX(x);
            trait.setY(y);
        });
        return CommandResponse.builder()
                .gameObjectModel(Collections.singletonList(gameObjectModel))
                .completed(true)
                .command(Optional.empty())
                .build();
    }
}
