package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.factory.TraitMapFactory;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.trait.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameObjectService {

    private List<GameObjectModel> gameObjectModelList;
    private Function<String, GameObjectModel> createObjectFunction;
    private Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper;

    public GameObjectService(final TraitMapFactory traitMapFactory,
                             final Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper) {
        this.point2DToSimpleMatrixMapper = Objects.requireNonNull(point2DToSimpleMatrixMapper, "point2DToSimpleMatrixMapper must not be null");
        gameObjectModelList = new ArrayList<>();
        createObjectFunction = (ownerId) -> {
            Map<Class, Trait> traitMap = traitMapFactory.getType("smallShip");
            GameObjectModel gameObjectModel = new GameObjectModel();
            traitMap.put(CommandTrait.class, new CommandTrait());
            traitMap.put(LocationTrait.class, LocationTrait
                    .builder()
                    .angle(0D)
                    .point2D(new Point2D(200, 200))
                    .build());
            traitMap.put(OwnerTrait.class, OwnerTrait.builder()
                    .ownerId(ownerId)
                    .build());
            traitMap.put(MetaTrait.class, MetaTrait.builder()
                    .id((long) gameObjectModelList.size())
                    .build());
            gameObjectModel.addAll(traitMap.values());
            gameObjectModel.getTrait(ModelTrait.class)
                    .map(modelTrait -> modelTrait.getVertexPointList()
                            .stream()
                            .map(point2D -> point2DToSimpleMatrixMapper.toRotationMatrix(modelTrait.getAngleOffset())
                                    .mult(point2DToSimpleMatrixMapper.toMatrix(point2D)))
                            .map(point2DToSimpleMatrixMapper::fromMatrix)
                            .collect(Collectors.toList()))
                    .ifPresent(pointList -> gameObjectModel.getTrait(ModelTrait.class).get().setVertexPointList(pointList));
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

    public GameObjectModel create(final Collection<Trait> traits) {
        List<Trait> finalList = traits.stream()
                .distinct()
                .filter(trait -> !trait.getClass().equals(MetaTrait.class))
                .collect(Collectors.toList());
        finalList.add(MetaTrait.builder()
                              .id((long) gameObjectModelList.size())
                              .build());
        GameObjectModel gameObjectModel = GameObjectModel.builder()
                .traitList(finalList)
                .build();

        gameObjectModel.getTrait(RenderTrait.class).orElseGet(() -> {
            gameObjectModel.addTrait(RenderTrait.builder().build());
            return null;
        });
        gameObjectModel.getTrait(ModelTrait.class)
                .map(modelTrait -> modelTrait.getVertexPointList()
                        .stream()
                        .map(point2D -> point2DToSimpleMatrixMapper.toRotationMatrix(modelTrait.getAngleOffset())
                                .mult(point2DToSimpleMatrixMapper.toMatrix(point2D)))
                        .map(point2DToSimpleMatrixMapper::fromMatrix)
                        .collect(Collectors.toList()))
                .ifPresent(pointList -> gameObjectModel.getTrait(ModelTrait.class).get().setVertexPointList(pointList));

        gameObjectModel.getTrait(LocationTrait.class).orElseGet(() -> {
            gameObjectModel.addTrait(LocationTrait.builder()
                                             .angle(0D)
                                             .point2D(new Point2D(0, 0))
                                             .build());
            return null;
        });
        gameObjectModelList.add(gameObjectModel);
        return gameObjectModel;
    }

    public void initializeForUser(final UserModel userModel) {
        gameObjectModelList.add(createObjectFunction.apply(userModel.getUsername()));
    }

    public void buildRender(final GameObjectModel gameObjectModel) {

        if (!gameObjectModel.getTrait(RenderTrait.class).isPresent()) {
            gameObjectModel.addTrait(RenderTrait.builder().build());
        }
        if (!gameObjectModel.getTrait(LocationTrait.class).isPresent()) {
            throw new RuntimeException("buildRender requirements is null");
        }
        if (!gameObjectModel.getTrait(ModelTrait.class).isPresent()) {
            throw new RuntimeException("buildRender requirements is null");
        }
        if (!gameObjectModel.getTrait(RenderTrait.class).isPresent()) {
            throw new RuntimeException("buildRender requirements is null");
        }
        LocationTrait locationTrait = gameObjectModel.getTrait(LocationTrait.class).get();
        ModelTrait modelTrait = gameObjectModel.getTrait(ModelTrait.class).get();
        RenderTrait renderTrait = gameObjectModel.getTrait(RenderTrait.class).get();

        List<Point2D> renderVertices = modelTrait.getVertexPointList()
                .stream()
                .map(point2D -> point2DToSimpleMatrixMapper
                        .toTranslationMatrix(locationTrait.getPoint2D())
                        .mult(point2DToSimpleMatrixMapper.toRotationMatrix(locationTrait.getAngle()))
                        .mult(point2DToSimpleMatrixMapper.toMatrix(point2D)))
                .map(point2DToSimpleMatrixMapper::fromMatrix)
                .collect(Collectors.toList());
        renderTrait.setColor(modelTrait.getVertexColor());
        renderTrait.setModelPointList(renderVertices);
    }

    public void reset() {
        this.gameObjectModelList.clear();
    }
}
