package ro.anud.globalCooldown.data.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.trait.MetaTrait;
import ro.anud.globalCooldown.data.trait.OwnerTrait;
import ro.anud.globalCooldown.data.trait.RenderTrait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class GameObjectRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectRepository.class);

    private AtomicLong sequence = new AtomicLong(0);
    private List<GameObjectModel> allGameObjectModelList;
    private List<GameObjectModel> innerGameObjectModel;
    private List<GameObjectModel> outerGameObjectModel;

    public GameObjectRepository() {
        allGameObjectModelList = new ArrayList<>();
        innerGameObjectModel = new ArrayList<>();
        outerGameObjectModel = new ArrayList<>();
    }

    public List<GameObjectModel> getAll() {
        return allGameObjectModelList;
    }

    public List<GameObjectModel> getAllInnerBlocking() {
        return innerGameObjectModel;
    }

    public List<GameObjectModel> getAllOuterBlocking() {
        return outerGameObjectModel;
    }

    public GameObjectModel getById(final long id) {
        LOGGER.info("getById " + id);
        GameObjectModel gameObjectModel = allGameObjectModelList.stream()
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
        Predicate<GameObjectModel> idEquals = gameObjectModel -> gameObjectModel
                .getTrait(MetaTrait.class)
                .get()
                .getId()
                .equals(id);

        this.innerGameObjectModel.removeIf(idEquals);
        this.outerGameObjectModel.removeIf(idEquals);
        this.allGameObjectModelList.removeIf(idEquals);
    }


    public void reset() {
        this.innerGameObjectModel.clear();
        this.outerGameObjectModel.clear();
        this.allGameObjectModelList.clear();
    }


    public void insertOuterBlocking(final GameObjectModel gameObjectModel) {
        insert(gameObjectModel);
        outerGameObjectModel.add(gameObjectModel);
    }

    public void insertInnerBlocking(final GameObjectModel gameObjectModel) {
        insert(gameObjectModel);
        innerGameObjectModel.add(gameObjectModel);
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
        allGameObjectModelList.add(gameObjectModel);
    }

    public Map<String, List<GameObjectModel>> getAllByOwner() {
        return allGameObjectModelList
                .stream()
                .collect(Collectors.groupingBy(gameObjectModel1 -> gameObjectModel1
                                 .getTrait(OwnerTrait.class)
                                 .map(OwnerTrait::getOwnerId)
                                 .orElse("")
                         )
                );
    }
}
