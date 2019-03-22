package ro.anud.globalCooldown.service;

import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.trait.MetaTrait;

@Service
public class GameObjectService {

    private GameObjectModel gameObjectModel;

    public GameObjectService() {
        this.gameObjectModel = new GameObjectModel();

        gameObjectModel.addTrait(LocationTrait.builder()
                .x(0)
                .y(0)
                .build()
        );

        gameObjectModel.addTrait(MetaTrait.builder()
                .id(0L)
                .name("Object Name")
                .build()
        );

        gameObjectModel.addTrait(new CommandTrait());
    }

    public GameObjectModel getById(int id) {
        return gameObjectModel;
    }
}
