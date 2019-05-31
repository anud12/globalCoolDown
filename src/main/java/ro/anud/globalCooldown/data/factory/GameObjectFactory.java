package ro.anud.globalCooldown.data.factory;

import javafx.geometry.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.data.mapper.Point2DToSimpleMatrixMapper;
import ro.anud.globalCooldown.data.model.GameObjectModel;
import ro.anud.globalCooldown.data.service.CalculateCentroidService;
import ro.anud.globalCooldown.data.trait.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameObjectFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectFactory.class);

    private final TraitMapFactory traitMapFactory;
    private final Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper;
    private final CalculateCentroidService calculateCentroidService;

    public GameObjectFactory(final TraitMapFactory traitMapFactory,
                             final Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper,
                             final CalculateCentroidService calculateCentroidService) {
        this.traitMapFactory = Objects.requireNonNull(traitMapFactory, "traitMapFactory must not be null");
        this.point2DToSimpleMatrixMapper = Objects.requireNonNull(point2DToSimpleMatrixMapper, "point2DToSimpleMatrixMapper must not be null");
        this.calculateCentroidService = Objects.requireNonNull(calculateCentroidService, "calculateCentroidService must not be null");
    }


    public List<GameObjectModel> loadMacroFromDisk(final String filename) {
        return traitMapFactory.getMacro(filename)
                .stream()
                .map(traitMap -> createFromTraits(new ArrayList<>(traitMap.values())))
                .collect(Collectors.toList());
    }

    public GameObjectModel createFromTraits(List<Trait> traitList) {
        Map<Class, Trait> classTraitMap = traitList.stream()
                .collect(HashMap::new,
                        (classTraitHashMap, trait) -> classTraitHashMap.put(trait.getClass(), trait),
                        HashMap::putAll);
        classTraitMap.putIfAbsent(RenderTrait.class, RenderTrait.builder().build());
        classTraitMap.putIfAbsent(CommandTrait.class, new CommandTrait());
        return new GameObjectModel(new ArrayList<>(classTraitMap.values()));
    }

    public GameObjectModel loadFromDisk(final String fileName,
                                        final Double scale) {
        Map<Class, Trait> classTraitMap = traitMapFactory.getDefinition(fileName);
        classTraitMap.put(LocationTrait.class, LocationTrait.builder()
                .point2D(new Point2D(0, 0))
                .angle(0D)
                .build());

        GameObjectModel gameObjectModel = createFromTraits(new ArrayList<>(classTraitMap.values()));

        gameObjectModel.getTrait(ModelTrait.class).ifPresent(modelTrait -> {
                    Point2D center = calculateCentroidService.calculateCentroid(modelTrait.getVertexPointList());
                    LOGGER.info(modelTrait.getVertexPointList().toString());
                    LOGGER.info(center.toString());
                    modelTrait.setVertexPointList(modelTrait.getVertexPointList()
                            .stream()
                            .map(point2D -> point2D.subtract(center))
                            .map(point2D -> point2DToSimpleMatrixMapper.toRotationMatrix(modelTrait.getAngleOffset())
                                    .mult(point2DToSimpleMatrixMapper.toScaleMatrix(1 / modelTrait.getFurtherPoint(), 1 / modelTrait.getFurtherPoint()))
                                    .mult(point2DToSimpleMatrixMapper.toScaleMatrix(scale, scale))
                                    .mult(point2DToSimpleMatrixMapper.toMatrix(point2D))
                            )
                            .map(point2DToSimpleMatrixMapper::fromMatrix)
                            .collect(Collectors.toList())
                    );
                    LOGGER.info(modelTrait.getVertexPointList().toString());
                    modelTrait.setFurtherPoint(scale);
                }
        );
        LOGGER.info(gameObjectModel.toString());

        return gameObjectModel;
    }
}
