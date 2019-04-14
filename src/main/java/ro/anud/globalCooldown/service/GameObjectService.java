package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.ejml.simple.SimpleMatrix;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.GameObjectModel;
import ro.anud.globalCooldown.model.UserModel;
import ro.anud.globalCooldown.trait.*;
import ro.anud.globalCooldown.validation.optionalValidation.OptionalValidation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameObjectService {

    private List<GameObjectModel> gameObjectModelList;
    private Function<String, GameObjectModel> createObjectFunction;
    private final OptionalValidation optionalValidation;

    public GameObjectService(final OptionalValidation optionalValidation) {
        this.optionalValidation = Objects.requireNonNull(optionalValidation, "optionalValidation must not be null");
        gameObjectModelList = new ArrayList<>();
        createObjectFunction = (ownerId) -> {
            GameObjectModel gameObjectModel = new GameObjectModel();

            gameObjectModel.addTrait(OwnerTrait.builder()
                                             .ownerId(ownerId)
                                             .build());

            gameObjectModel.addTrait(LocationTrait.builder()
                                             .point2D(new Point2D(200, 200))
                                             .modelVertices(Arrays.asList(
                                                     new Point2D(-10D, 10D),
                                                     new Point2D(10D, 5D),
                                                     new Point2D(10D, -5D),
                                                     new Point2D(-10D, -10D)
                                             ))
                                             .angle(0D)
                                             .build()
            );

            gameObjectModel.addTrait(MetaTrait.builder()
                                             .id((long) gameObjectModelList.size())
                                             .build()
            );

            gameObjectModel.addTrait(RenderTrait.builder()
                                             .modelPointList(Arrays.asList(
                                                     new Point2D(-10D, -10D),
                                                     new Point2D(10D, -10D),
                                                     new Point2D(10D, 10D),
                                                     new Point2D(-10D, 10D)
                                             ))
                                             .color(Color.CYAN)
                                             .build());

            gameObjectModel.addTrait(new CommandTrait());

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

    public void create(final List<Trait> traits) {
        List<Trait> finalList = traits.stream()
                .distinct()
                .filter(trait -> !trait.getClass().equals(MetaTrait.class))
                .collect(Collectors.toList());
        finalList.add(MetaTrait.builder()
                              .id((long) gameObjectModelList.size())
                              .build());
        gameObjectModelList.add(GameObjectModel.builder()
                                        .traitList(finalList)
                                        .build());
    }

    public void initializeForUser(final UserModel userModel) {
        gameObjectModelList.add(createObjectFunction.apply(userModel.getUsername()));
    }

    public void buildRender(final GameObjectModel gameObjectModel) {
        if (optionalValidation.createChain()
                .validate(gameObjectModel.getTrait(LocationTrait.class))
                .validate(gameObjectModel.getTrait(RenderTrait.class))
                .isAnyNotPresent()) {
            return;
        }
        LocationTrait locationTrait = gameObjectModel.getTrait(LocationTrait.class).get();
        RenderTrait renderTrait = gameObjectModel.getTrait(RenderTrait.class).get();

        List<Point2D> renderVertices = locationTrait.getModelVertices()
                .stream()
                .map(point2D -> {
                    SimpleMatrix translation = toTranslationMatrix(locationTrait.getPoint2D());
                    SimpleMatrix rotation = toRotationMatrix(locationTrait.getAngle());
                    SimpleMatrix vector = toMatrix(point2D);
                    return translation
                            .mult(rotation)
                            .mult(vector);

                })
                .map(this::fromMatrix)
                .collect(Collectors.toList());
        renderTrait.setModelPointList(renderVertices);
    }

    private SimpleMatrix toTranslationMatrix(Point2D point2D) {
        double[][] doubles = new double[][]
                {
                        {1,
                                0,
                                0,
                                point2D.getX()
                        },
                        {0,
                                1,
                                0,
                                point2D.getY()
                        },
                        {0,
                                0,
                                1,
                                0
                        },
                        {0,
                                0,
                                0,
                                1
                        },
                };
        return new SimpleMatrix(doubles);
    }

    private SimpleMatrix toRotationMatrix(Double angle) {
        Double radians = Math.toRadians(angle);
        double[][] doubles = new double[][]
                {
                        {Math.cos(radians),
                                Math.sin(radians),
                                0,
                                0
                        },
                        {-Math.sin(radians),
                                Math.cos(radians),
                                0,
                                0
                        },
                        {0,
                                0,
                                1,
                                0
                        },
                        {0,
                                0,
                                0,
                                1
                        },
                };
        return new SimpleMatrix(doubles);
    }

    private Point2D fromMatrix(SimpleMatrix simpleMatrix) {
        return new Point2D(simpleMatrix.get(0, 0), simpleMatrix.get(1, 0));
    }

    private SimpleMatrix toMatrix(Point2D point2D) {
        double[][] doubles = new double[][]
                {
                        {point2D.getX()},
                        {point2D.getY()},
                        {1D},
                        {1D}
                };

        return new SimpleMatrix(doubles);
    }
}
