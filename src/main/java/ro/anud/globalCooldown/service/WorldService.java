package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.emitter.WorldEmitter;
import ro.anud.globalCooldown.factory.TraitMapFactory;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.trait.ModelTrait;
import ro.anud.globalCooldown.trait.OwnerTrait;
import ro.anud.globalCooldown.trait.Trait;
import ro.anud.globalCooldown.trigger.Trigger;
import ro.anud.globalCooldown.trigger.VictoryTrigger;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorldService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldService.class);
    private final GameObjectService gameObjectService;
    private final UserService userService;
    private final WorldEmitter worldEmitter;
    private PointIsInsidePointList pointIsInsidePointList;
    private final TraitMapFactory traitMapFactory;
    private final Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper;

    private List<Trigger> triggerList;
    private GameObjectModel victoryGameObjectModel;
    private List<GameObjectModel> blockGameObjectModelList;

    public WorldService(final GameObjectService gameObjectService,
                        final UserService userService,
                        final WorldEmitter worldEmitter,
                        final PointIsInsidePointList pointIsInsidePointList,
                        final TraitMapFactory traitMapFactory,
                        final Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper) {
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.worldEmitter = Objects.requireNonNull(worldEmitter, "worldEmitter must not be null");
        this.pointIsInsidePointList = Objects.requireNonNull(pointIsInsidePointList, "pointIsInsidePointList must not be null");
        this.traitMapFactory = Objects.requireNonNull(traitMapFactory, "traitMapFactory must not be null");
        this.point2DToSimpleMatrixMapper = Objects.requireNonNull(point2DToSimpleMatrixMapper, "point2DToSimpleMatrixMapper must not be null");
        blockGameObjectModelList = new ArrayList<>();
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

    public List<GameObjectModel> getBlockGameObjectModelList() {
        return blockGameObjectModelList;
    }

    public boolean isNotBlocked(Point2D gamePoint) {
        return this.blockGameObjectModelList
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
        this.gameObjectService.reset();
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
        blockGameObjectModelList.add(this.gameObjectService.create(triangleSquare.values()));

        Map<Class, Trait> blockSquare = traitMapFactory.getType("blockSquare");
        blockSquare.put(OwnerTrait.class,
                        OwnerTrait.builder()
                                .ownerId("")
                                .build());
        blockGameObjectModelList.add(this.gameObjectService.create(blockSquare.values()));

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
        this.victoryGameObjectModel = this.gameObjectService.create(victoryTrait.values());
    }
}
