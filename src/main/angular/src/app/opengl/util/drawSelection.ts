import {Point2D} from "../../java.models";
import {applyCameraOffset} from "./applyCameraOffset";
import {convertCoordinatesToGL} from "./convertCoordinatesToGL.function";

export const drawSelection = (radius: number,
                              position: { x: number, y: number },
                              camera: { x: number, y: number, scale: number },
                              clientRect: ClientRect) => {
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
    return array;
}