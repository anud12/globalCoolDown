package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.emitter.WorldEmitter;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.trait.LocationTrait;
import ro.anud.globalCooldown.trait.OwnerTrait;
import ro.anud.globalCooldown.trait.RenderTrait;
import ro.anud.globalCooldown.trigger.Trigger;
import ro.anud.globalCooldown.trigger.VictoryTrigger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class WorldService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldService.class);
    private final GameObjectService gameObjectService;
    private final UserService userService;
    private final WorldEmitter worldEmitter;

    private List<Trigger> triggerList;
    private GameObjectModel gameObjectModel;

    public WorldService(final GameObjectService gameObjectService,
                        final UserService userService, final WorldEmitter worldEmitter) {
        this.gameObjectService = Objects.requireNonNull(gameObjectService, "gameObjectService must not be null");
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
        this.worldEmitter = Objects.requireNonNull(worldEmitter, "worldEmitter must not be null");
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

    public GameObjectModel getGameObjectModel() {
        return gameObjectModel;
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
        this.gameObjectService
                .create(Arrays.asList(LocationTrait.builder()
                                              .point2D(new Point2D(0, 0))
                                              .modelVertices(Arrays.asList(
                                                      new Point2D(0D, 0D),
                                                      new Point2D(500D, 0D),
                                                      new Point2D(500D, 500D),
                                                      new Point2D(0D, 500D)
                                              ))
                                              .angle(0D)
                                              .build(),
                                      RenderTrait.builder()
                                              .modelPointList(Arrays.asList(
                                                      new Point2D(-0D, -10D),
                                                      new Point2D(10D, -0D),
                                                      new Point2D(0D, 10D),
                                                      new Point2D(-10D, 0D)
                                              ))
                                              .color(Color.CADETBLUE)
                                              .build(),
                                      OwnerTrait.builder()
                                              .ownerId("")
                                              .build()));
        this.gameObjectModel = this.gameObjectService
                .create(Arrays.asList(LocationTrait.builder()
                                              .point2D(new Point2D(400, 400))
                                              .modelVertices(Arrays.asList(
                                                      new Point2D(-0D, -10D),
                                                      new Point2D(10D, -0D),
                                                      new Point2D(0D, 10D),
                                                      new Point2D(-10D, 0D)
                                              ))
                                              .angle(0D)
                                              .build(),
                                      RenderTrait.builder()
                                              .modelPointList(Arrays.asList(
                                                      new Point2D(-0D, -10D),
                                                      new Point2D(10D, -0D),
                                                      new Point2D(0D, 10D),
                                                      new Point2D(-10D, 0D)
                                              ))
                                              .color(Color.MAGENTA)
                                              .build(),
                                      OwnerTrait.builder()
                                              .ownerId("")
                                              .build()));
    }


}
