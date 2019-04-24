package ro.anud.globalCooldown.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.MetaTrait;
import ro.anud.globalCooldown.trait.OwnerTrait;
import ro.anud.globalCooldown.trait.RenderTrait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class GameObjectRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectRepository.class);

    private AtomicLong sequence = new AtomicLong(0);
    private List<GameObjectModel> gameObjectModelList;

    public GameObjectRepository() {
        gameObjectModelList = new ArrayList<>();
    }

    public List<GameObjectModel> getAll() {
        return gameObjectModelList;
    }

    public GameObjectModel getById(final long id) {
        LOGGER.info("getById " + id);
        GameObjectModel gameObjectModel = gameObjectModelList.stream()
                .filter(gameObjectModel1 -> gameObjectModel1
                        .getTrait(MetaTrait.class)
                        .get()
                        .getId()
                        .equals(id)
                )
                .findAny()
                .get();
        LOGGER.info(gameObjectModel.toString());
        return gameObjectModel;
    }

    public void deleteById(final Long id) {
        this.gameObjectModelList = gameObjectModelList.stream()
                .filter(gameObjectModel -> gameObjectModel
                        .getTrait(MetaTrait.class)
                        .get()
                        .getId()
                        .equals(id)
                )
                .collect(Collectors.toList());
    }


    public void reset() {
        this.gameObjectModelList.clear();
    }

    public void insert(final GameObjectModel gameObjectModel) {
        gameObjectModel.addTrait(MetaTrait.builder()
                                         .id(sequence.getAndAdd(1))
                                         .build());

        gameObjectModel.getTrait(RenderTrait.class).orElseGet(() -> {
            gameObjectModel.addTrait(RenderTrait.builder()
                                             .build());
            return null;
        });
        gameObjectModelList.add(gameObjectModel);
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
}
