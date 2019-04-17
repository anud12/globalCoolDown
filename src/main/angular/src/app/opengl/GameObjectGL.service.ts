import {GameObjectModel, LocationTrait, RenderTrait} from "../java.models";
import {convertCoordinatesToGL} from "./util/convertCoordinatesToGL.function";

export class GameObjectGLService {

    public getVertexShaderSource() {
        return `
            attribute vec4 a_Position;
            void main() {
                gl_Position = a_Position;
                gl_PointSize = 2.0;
            }
            `
    }

    public getFragmentShaderSource() {
        return `
            precision mediump float;
            uniform vec4 u_FragColor;
            void main() {
                gl_FragColor = u_FragColor;
            }
    `
    }

    public getColorArray(gameObjectModel: GameObjectModel) {
        const renderTrait = gameObjectModel.traitMap.RenderTrait as RenderTrait;
        const array = [];
        array.push(renderTrait.color.red);
        array.push(renderTrait.color.green);
        array.push(renderTrait.color.blue);
        array.push(1);
        return array;
    }

    public transformGameObject(gameObjectModel: GameObjectModel,
                               clientRectangle: ClientRect,
                               camera: { x: number, y: number, scale: number }): Array<number> {
        const array = [];
        const locationTrait = gameObjectModel.traitMap.LocationTrait as LocationTrait;
        const renderTrait = gameObjectModel.traitMap.RenderTrait as RenderTrait;
        renderTrait.modelPointList
            .map(value => {
                return {
                    x: (value.x + camera.x) * camera.scale,
                    y: (value.y + camera.y) * camera.scale
                }
            })
            .map(value => convertCoordinatesToGL(value, clientRectangle))
            .map(value => {
                array.push(value.x);
                array.push(value.y);
            });
        return array;
    }
}