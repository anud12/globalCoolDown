package ro.anud.globalCooldown.data.service;

import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.data.factory.GameObjectFactory;
import ro.anud.globalCooldown.data.mapper.Point2DToSimpleMatrixMapper;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.model.UserModel;
import ro.anud.globalCooldown.data.repository.GameObjectRepository;
import ro.anud.globalCooldown.data.trait.LocationTrait;
import ro.anud.globalCooldown.data.trait.ModelTrait;
import ro.anud.globalCooldown.data.trait.OwnerTrait;
import ro.anud.globalCooldown.data.trait.RenderTrait;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GameObjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectService.class);

    private Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper;
    private final GameObjectRepository gameObjectRepository;
    private GameObjectFactory gameObjectFactory;

    public GameObjectService(final Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper,
                             final GameObjectRepository gameObjectRepository,
                             final GameObjectFactory gameObjectFactory) {
        this.point2DToSimpleMatrixMapper = Objects.requireNonNull(point2DToSimpleMatrixMapper, "point2DToSimpleMatrixMapper must not be null");
        this.gameObjectRepository = Objects.requireNonNull(gameObjectRepository, "gameObjectRepository must not be null");
        this.gameObjectFactory = Objects.requireNonNull(gameObjectFactory, "gameObjectFactory must not be null");
    }


    public void initializeForUser(final UserModel userModel) {
        GameObjectModel gameObjectModel = gameObjectFactory
                .loadFromDisk("smallShip",
                              LocationTrait.builder()
                                      .angle(0D)
                                      .point2D(new Point2D(200, 200))
                                      .build(),
                              OwnerTrait.builder()
                                      .ownerId(userModel.getUsername())
                                      .build(),
                              point2DToSimpleMatrixMapper.toScaleMatrix(20, 20)
                );
        gameObjectRepository.insert(gameObjectModel);
    }

    public void buildRender(final GameObjectModel gameObjectModel) {
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
        renderTrait.setVertexColor(modelTrait.getVertexColor());
        renderTrait.setPolygonColor(modelTrait.getPolygonColor());
        renderTrait.setModelPointList(renderVertices);
    }
}
