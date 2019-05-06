import {Point2D} from "../../java.models";
import {applyCameraOffset} from "./applyCameraOffset";
import {convertCoordinatesToGL} from "./convertCoordinatesToGL.function";

function circle(radius: number): { vertices: Array<Point2D>, size: number } {
    const circleRadius = radius;
    const number: Array<Point2D> = [];
    var vertexCount = 100;
    for (var i = 0; i < vertexCount; i++) {
        number.push({
            x: circleRadius * Math.cos((i / vertexCount) * 2.0 * Math.PI),
            y: circleRadius * Math.sin((i / vertexCount) * 2.0 * Math.PI)
        });
    }
    return {
        vertices: number,
        size: number.length
    };
}

function rectangle(radius: number): { vertices: Array<Point2D>, size: number } {
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
    return {
        vertices: number,
        size: number.length
    };
}

export const drawSelection = (radius: number,
                              position: { x: number, y: number },
                              camera: { x: number, y: number, scale: number },
                              clientRect: ClientRect): { points, size } => {

    const {vertices, size} = circle(radius);
    const array = [];
    applyCameraOffset(vertices.map(value => {
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
        size: size
    };
}