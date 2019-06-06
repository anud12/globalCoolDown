import {GameObjectModel, RenderTrait} from "../java.models";
import {convertCoordinatesToGL} from "./util/convertCoordinatesToGL.function";
import {applyCameraOffset} from "./util/applyCameraOffset";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ShaderHttpEndpoint} from "../endpoints/shader.http.endpoints";

export class GameObjectGLService {

    constructor(private httpClient: HttpClient) {

    }

    public getVertexShaderSource(): Observable<string> {
        return this.httpClient.get(ShaderHttpEndpoint + "/simple.vert", {responseType: 'text'});
    }

    public getFragmentShaderSource(): Observable<string> {
        return this.httpClient.get (ShaderHttpEndpoint + "/simple.frag", {responseType: 'text'});
    }


    public getPolygonColorArray(gameObjectModel: GameObjectModel) {
        const renderTrait = gameObjectModel.traitMap.RenderTrait as RenderTrait;
        const array = [];
        array.push(renderTrait.polygonColor.red);
        array.push(renderTrait.polygonColor.green);
        array.push(renderTrait.polygonColor.blue);
        array.push(renderTrait.polygonColor.alpha);
        return array;
    }

    public getVertexColorArray(gameObjectModel: GameObjectModel) {
        const renderTrait = gameObjectModel.traitMap.RenderTrait as RenderTrait;
        const array = [];
        array.push(renderTrait.vertexColor.red);
        array.push(renderTrait.vertexColor.green);
        array.push(renderTrait.vertexColor.blue);
        array.push(renderTrait.vertexColor.alpha);
        return array;
    }

    public transformGameObject(gameObjectModel: GameObjectModel,
                               clientRectangle: ClientRect,
                               camera: { x: number, y: number, scale: number }): Array<number> {
        const array = [];
        const renderTrait = gameObjectModel.traitMap.RenderTrait as RenderTrait;
        applyCameraOffset(renderTrait.modelPointList, camera)
            .map(value => convertCoordinatesToGL(value, clientRectangle))
            .map(value => {
                array.push(value.x);
                array.push(value.y);
            });
        return array;
    }
}