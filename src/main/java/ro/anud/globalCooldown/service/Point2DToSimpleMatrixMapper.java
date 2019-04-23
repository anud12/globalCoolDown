package ro.anud.globalCooldown.service;

import javafx.geometry.Point2D;
import org.ejml.simple.SimpleMatrix;
import org.springframework.stereotype.Service;

@Service
public class Point2DToSimpleMatrixMapper {
    public SimpleMatrix toScaleMatrix(double x, double y) {
        double[][] doubles = new double[][]{
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        };
        return new SimpleMatrix(doubles);
    }

    public SimpleMatrix toTranslationMatrix(Point2D point2D) {
        double x = point2D.getX();
        double y = point2D.getY();
        double[][] doubles = new double[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        };
        return new SimpleMatrix(doubles);
    }

    public SimpleMatrix toRotationMatrix(Double angle) {
        Double radians = Math.toRadians(angle);
        double[][] doubles = new double[][]{
                {Math.cos(radians), Math.sin(radians), 0, 0},
                {-Math.sin(radians), Math.cos(radians), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        };
        return new SimpleMatrix(doubles);
    }

    public Point2D fromMatrix(SimpleMatrix simpleMatrix) {
        return new Point2D(simpleMatrix.get(0, 0), simpleMatrix.get(1, 0));
    }

    public SimpleMatrix toMatrix(Point2D point2D) {
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
