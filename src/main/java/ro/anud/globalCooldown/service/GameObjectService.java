package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.trait.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameObjectService {

    private List<GameObjectModel> gameObjectModelList;
    private Function<String, GameObjectModel> createObjectFunction;
    private GameObjectModel gameObjectModel;

    public GameObjectService() {
        gameObjectModelList = new ArrayList<>();
        createObjectFunction = (ownerId) -> {
            GameObjectModel gameObjectModel = new GameObjectModel();

            gameObjectModel.addTrait(OwnerTrait.builder()
                                             .ownerId(ownerId)
                                             .build());

            gameObjectModel.addTrait(LocationTrait.builder()
                                             .point2D(new Point2D(200, 200))
                                             .build()
            );

            gameObjectModel.addTrait(MetaTrait.builder()
                                             .id((long) gameObjectModelList.size())
                                             .build()
            );

            gameObjectModel.addTrait(RenderTrait.builder()
                                             .modelPointList(Arrays.asList(
                                                     new Point2D(-10D, -10D),
                                                     new Point2D(10D, -10D),
                                                     new Point2D(10D, 10D),
                                                     new Point2D(-10D, 10D)
                                             ))
                                             .color(Color.CYAN)
                                             .build());

            gameObjectModel.addTrait(new CommandTrait());

            return gameObjectModel;
        };
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

    public void initializeForUser(final UserModel userModel) {
        gameObjectModelList.add(createObjectFunction.apply(userModel.getUsername()));
    }
}
