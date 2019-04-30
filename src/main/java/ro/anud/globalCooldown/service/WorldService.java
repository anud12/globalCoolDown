package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.emitter.WorldEmitter;
import ro.anud.globalCooldown.factory.GameObjectFactory;
import ro.anud.globalCooldown.factory.TraitMapFactory;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.repository.GameObjectRepository;
import ro.anud.globalCooldown.trait.*;
import ro.anud.globalCooldown.trigger.DeleteGameObjectTrigger;
import ro.anud.globalCooldown.trigger.Trigger;
import ro.anud.globalCooldown.trigger.TriggerDelay;
import ro.anud.globalCooldown.trigger.VictoryTrigger;

import java.util.*;
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
        return gameObjectRepository.getAllOuterBlocking()
                .stream()
                .noneMatch(blockGameObjectModel -> {
                    ModelTrait blockModelTrait = blockGameObjectModel.getTrait(ModelTrait.class).get();
                    LocationTrait blockLocationTrait = blockGameObjectModel.getTrait(LocationTrait.class).get();
                    return pointIsInsidePointList.isInside(blockModelTrait.getVertexPointList()
                                                                   .stream()
                                                                   .map(point2D -> point2D.add(blockLocationTrait.getPoint2D()))
                                                                   .collect(Collectors.toList()),
                                                           gamePoint);
                });
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
        Map<Class, Trait> triangleSquare = traitMapFactory.getType("rectangleSquare");
        triangleSquare.put(OwnerTrait.class,
                           OwnerTrait.builder()
                                   .ownerId("")
                                   .build());
        triangleSquare.put(LocationTrait.class,
                           LocationTrait.builder()
                                   .point2D(new Point2D(500, 0))
                                   .angle(0D)
                                   .build());
        GameObjectModel triangleGameObject = this.gameObjectFactory.createFromTraits(new ArrayList<>(triangleSquare.values()));
        gameObjectRepository.insertOuterBlocking(triangleGameObject);

        Map<Class, Trait> blockSquare = traitMapFactory.getType("blockSquare");
        blockSquare.put(OwnerTrait.class,
                        OwnerTrait.builder()
                                .ownerId("")
                                .build());
        blockSquare.put(LocationTrait.class,
                        LocationTrait.builder()
                                .point2D(new Point2D(0, 0))
                                .angle(0D)
                                .build());
        GameObjectModel blockGameObject = this.gameObjectFactory.createFromTraits(new ArrayList<>(blockSquare.values()));
        gameObjectRepository.insertOuterBlocking(blockGameObject);

        Map<Class, Trait> victoryTrait = traitMapFactory.getType("victory");
        victoryTrait.put(OwnerTrait.class,
                         OwnerTrait.builder()
                                 .ownerId("")
                                 .build());
        victoryTrait.put(LocationTrait.class,
                         LocationTrait.builder()
                                 .point2D(new Point2D(400, 400))
                                 .angle(0D)
                                 .build());

        this.victoryGameObjectModel = this.gameObjectFactory.createFromTraits(new ArrayList<>(victoryTrait.values()));
        gameObjectRepository.insertOuterBlocking(this.victoryGameObjectModel);
        this.triggerList.add(new TriggerDelay(20000L, new DeleteGameObjectTrigger(triangleGameObject.getTrait(MetaTrait.class).get().getId())));
    }
}
