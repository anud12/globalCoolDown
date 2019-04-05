package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

            gameObjectModel.addTrait(OwnerTrait.builder()
                                             .ownerId("admin")
                                             .build());

            gameObjectModel.addTrait(LocationTrait.builder()
                                             .point2D(new Point2D(200, 200))
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

    public Map<String, List<GameObjectModel>> getAllByOwner() {
        return gameObjectModelList
                .stream()
                .collect(Collectors.groupingBy(gameObjectModel1 -> gameObjectModel1
                                 .getTrait(OwnerTrait.class)
                                 .map(OwnerTrait::getOwnerId)
                                 .orElse("")
                         )
                );
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
