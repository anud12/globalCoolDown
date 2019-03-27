package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.CommandTrait;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.trait.MetaTrait;
import ro.anud.globalCooldown.trait.Trait;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class GameObjectService {

    private List<GameObjectModel> gameObjectModelList;
    private Supplier<GameObjectModel> createObject;
    private GameObjectModel gameObjectModel;

    public GameObjectService() {
        gameObjectModelList = new ArrayList<>();
        createObject = () -> {
            GameObjectModel gameObjectModel = new GameObjectModel();

            gameObjectModel.addTrait(LocationTrait.builder()
                    .point2D(new Point2D(0, 0))
                    .build()
            );

            gameObjectModel.addTrait(MetaTrait.builder()
                    .id(0L)
                    .build()
            );

            gameObjectModel.addTrait(new CommandTrait());
            return gameObjectModel;
        };
        gameObjectModelList.add(createObject.get());
    }

    public List<GameObjectModel> getAll() {
        return gameObjectModelList;
    }

    public GameObjectModel getById(final long id) {
        return gameObjectModelList.get((int) id);
    }

    public void create(final List<Trait> traits) {
        List<Trait> finalList = traits.stream()
                .distinct()
                .filter(trait -> !trait.getClass().equals(MetaTrait.class))
                .collect(Collectors.toList());
        finalList.add(MetaTrait.builder()
                .id((long) gameObjectModelList.size())
                .build());
        gameObjectModelList.add(GameObjectModel.builder()
                .traitList(finalList)
                .build());
    }
}
