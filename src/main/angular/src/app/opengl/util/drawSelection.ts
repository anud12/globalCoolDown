import {Point2D} from "../../java.models";
import {applyCameraOffset} from "./applyCameraOffset";
import {convertCoordinatesToGL} from "./convertCoordinatesToGL.function";

function circle(radius: number): Array<Point2D> {
    const circleRadius = radius;
    const number: Array<Point2D> = [];
    var vertexCount = 100;
    for (var i = 0; i < vertexCount; i++) {
        number.push({
            x: circleRadius * Math.cos((i / vertexCount) * 2.0 * Math.PI),
            y: circleRadius * Math.sin((i / vertexCount) * 2.0 * Math.PI)
        });
    }
    return number;
}

function rectangle(radius: number): Array<Point2D> {
    const number: Array<Point2D> = [];
    number.push({
        x: -radius,
        y: radius
    });
    number.push({
        x: radius,
        y: radius
    });
    number.push({
        x: radius,
        y: -radius
    });
    number.push({
        x: -radius,
        y: -radius
    });
    return number;
}

export const drawSelection = (radius: number,
                              position: { x: number, y: number },
                              camera: { x: number, y: number, scale: number },
                              clientRect: ClientRect): { points, size } => {

    const number = circle(radius);
    const array = [];
    applyCameraOffset(number.map(value => {
        return {
            x: value.x + position.x,
            y: value.y + position.y
        }
    }), camera)

        .map(value => convertCoordinatesToGL(value, clientRect))

        .map(value => {
            array.push(value.x);
            array.push(value.y);
        });
    return {
        points: array,
        size: 100
    };
}