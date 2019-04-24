package ro.anud.globalCooldown.factory;

import org.ejml.simple.SimpleMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.mapper.Point2DToSimpleMatrixMapper;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.trait.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GameObjectFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameObjectFactory.class);

    private final TraitMapFactory traitMapFactory;
    private final Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper;

    public GameObjectFactory(final TraitMapFactory traitMapFactory,
                             final Point2DToSimpleMatrixMapper point2DToSimpleMatrixMapper) {
        this.traitMapFactory = Objects.requireNonNull(traitMapFactory, "traitMapFactory must not be null");
        this.point2DToSimpleMatrixMapper = Objects.requireNonNull(point2DToSimpleMatrixMapper, "point2DToSimpleMatrixMapper must not be null");
    }


    public GameObjectModel loadFromDisk(final String fileName,
                                        final LocationTrait locationTrait,
                                        final OwnerTrait ownerTrait,
                                        final SimpleMatrix scaleMatrix) {
        Map<Class, Trait> classTraitMap = traitMapFactory.getType(fileName);
        classTraitMap.put(OwnerTrait.class, ownerTrait);
        classTraitMap.put(LocationTrait.class, locationTrait);
        classTraitMap.put(CommandTrait.class, new CommandTrait());

        GameObjectModel gameObjectModel = new GameObjectModel(new ArrayList<>(classTraitMap.values()));
        gameObjectModel.getTrait(ModelTrait.class).ifPresent(modelTrait -> modelTrait
                .setVertexPointList(modelTrait.getVertexPointList()
                                            .stream()
                                            .map(point2D -> point2DToSimpleMatrixMapper.toRotationMatrix(modelTrait.getAngleOffset())
                                                    .mult(scaleMatrix)
                                                    .mult(point2DToSimpleMatrixMapper.toMatrix(point2D))
                                            )
                                            .map(point2DToSimpleMatrixMapper::fromMatrix)
                                            .collect(Collectors.toList())
                )
        );
        return gameObjectModel;
    }
}
