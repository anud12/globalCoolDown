package ro.anud.globalCooldown.data.service;

import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.api.emitter.WorldEmitter;
import ro.anud.globalCooldown.data.factory.GameObjectFactory;
import ro.anud.globalCooldown.data.factory.TraitMapFactory;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.model.UserModel;
import ro.anud.globalCooldown.data.repository.GameObjectRepository;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.data.trait.ModelTrait;
import ro.anud.globalCooldown.data.trait.OwnerTrait;
import ro.anud.globalCooldown.data.trait.Trait;
import ro.anud.globalCooldown.engine.trigger.Trigger;
import ro.anud.globalCooldown.engine.trigger.type.VictoryTrigger;
import ro.anud.globalCooldown.api.service.UserService;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class WorldService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldService.class);
    private final GameObjectFactory gameObjectFactory;
    private final GameObjectRepository gameObjectRepository;
    private final UserService userService;
    private final WorldEmitter worldEmitter;
    private PointIsInsidePointList pointIsInsidePointList;
    private final TraitMapFactory traitMapFactory;

    private List<Trigger> triggerList;
    private GameObjectModel victoryGameObjectModel;

    public WorldService(final GameObjectFactory gameObjectFactory,
                        final GameObjectRepository gameObjectRepository,
                        final UserService userService,
                        final WorldEmitter worldEmitter,
                        final PointIsInsidePointList pointIsInsidePointList,
                        final TraitMapFactory traitMapFactory) {
        this.gameObjectFactory = Objects.requireNonNull(gameObjectFactory, "gameObjectFactory must not be null");
        this.gameObjectRepository = Objects.requireNonNull(gameObjectRepository, "gameObjectRepository must not be null");
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.worldEmitter = Objects.requireNonNull(worldEmitter, "worldEmitter must not be null");
        this.pointIsInsidePointList = Objects.requireNonNull(pointIsInsidePointList, "pointIsInsidePointList must not be null");
        this.traitMapFactory = Objects.requireNonNull(traitMapFactory, "traitMapFactory must not be null");
        triggerList = new ArrayList<>();
        triggerList.add(new VictoryTrigger());
        create();
    }


    public void setTriggerList(List<Trigger> triggerList) {
        this.triggerList = triggerList;
    }

    public List<Trigger> getTriggerList() {
        return triggerList;
    }

    public GameObjectModel getVictoryGameObjectModel() {
        return victoryGameObjectModel;
    }

    public boolean isNotBlocked(Point2D gamePoint) {
        Predicate<GameObjectModel> isInside = blockGameObjectModel -> {
            ModelTrait blockModelTrait = blockGameObjectModel.getTrait(ModelTrait.class).get();
            LocationTrait blockLocationTrait = blockGameObjectModel.getTrait(LocationTrait.class).get();
            return pointIsInsidePointList.isInside(blockModelTrait.getVertexPointList()
                                                           .stream()
                                                           .map(point2D -> point2D.add(blockLocationTrait.getPoint2D()))
                                                           .collect(Collectors.toList()),
                                                   gamePoint);
        };
        return gameObjectRepository.getAllOuterBlocking()
                .stream()
                .noneMatch(isInside)
                || gameObjectRepository.getAllInnerBlocking()
                .stream()
                .anyMatch(isInside);
    }

    public void reset() {
        this.userService.getUserModelList()
                .stream()
                .map(UserModel::getUsername)
                .forEach(s -> worldEmitter.to(s, Arrays.asList()));
        this.gameObjectRepository.reset();
        this.userService.reset();
    }

    public void create() {
        gameObjectFactory.loadMacroFromDisk("worldOuterBlock")
                .stream()
                .peek(gameObjectModel -> gameObjectModel.addTrait(OwnerTrait.builder()
                                                                          .ownerId("")
                                                                          .build()))
                .forEach(gameObjectRepository::insertOuterBlocking);
        gameObjectFactory.loadMacroFromDisk("worldInnerBlock")
                .stream()
                .peek(gameObjectModel -> gameObjectModel.addTrait(OwnerTrait.builder()
                                                                          .ownerId("")
                                                                          .build()))
                .forEach(gameObjectRepository::insertInnerBlocking);
        Map<Class, Trait> victoryTrait = traitMapFactory.getDefinition("victory");
        victoryTrait.put(OwnerTrait.class,
                         OwnerTrait.builder()
                                 .ownerId("")
                                 .build());
        victoryTrait.put(LocationTrait.class,
                         LocationTrait.builder()
                                 .point2D(new Point2D(600, 600))
                                 .angle(0D)
                                 .build());

        this.victoryGameObjectModel = this.gameObjectFactory.createFromTraits(new ArrayList<>(victoryTrait.values()));
        gameObjectRepository.insertOuterBlocking(this.victoryGameObjectModel);
        //        this.triggerList.add(new TriggerDelay(20000L, new DeleteGameObjectTrigger(triangleGameObject.getTrait(MetaTrait.class).get().getId())));
    }
}
