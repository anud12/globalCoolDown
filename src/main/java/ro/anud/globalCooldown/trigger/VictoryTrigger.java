package ro.anud.globalCooldown.trigger;

import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.trait.MetaTrait;
import ro.anud.globalCooldown.trait.OwnerTrait;

import java.util.Optional;

public class VictoryTrigger implements Trigger {

    private static final Logger LOGGER = LoggerFactory.getLogger(VictoryTrigger.class);

    @Override
    public TriggerResponse execute(final TriggerArguments triggerArguments) {
        GameObjectModel gameObjectModel = triggerArguments
                .getWorldService()
                .getGameObjectModel();
        Point2D victoryPoint = gameObjectModel
                .getTrait(LocationTrait.class)
                .get()
                .getPoint2D();
        Optional optional = triggerArguments.getGameObjectService().getAll()
                .stream()
                .filter(gameObjectModel1 -> !gameObjectModel1.getTrait(OwnerTrait.class).get().getOwnerId().equals(""))
                .map(gameObjectModel1 -> gameObjectModel1.getTrait(LocationTrait.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(LocationTrait::getPoint2D)
                .filter(point2D1 -> point2D1.distance(victoryPoint) < 5)
                .findAny();
        if (optional.isPresent()) {
            triggerArguments.getWorldService().reset();
            triggerArguments.getWorldService().create();
        }
        return TriggerResponse.builder()
                .nextTrigger(this)
                .build();
    }
}
