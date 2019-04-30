/* tslint:disable */
// Generated using typescript-generator version 2.12.476 on 2019-04-30 14:24:27.

export class GameObjectModel {
    traitMap: { [index: string]: Trait };
}

export class GameObjectModelBuilder {
}

export class Point {
    x: number;
    y: number;
}

export class PointBuilder {
}

export class RGBA {
    red: number;
    green: number;
    blue: number;
    alpha: number;
}

export class RGBABuilder {
}

export class UserModel {
    username: string;
}

export class UserModelBuilder {
}

export class CommandTrait implements Trait {
    commandList: Command[];
}

export class LocationTrait implements Trait {
    point2D: Point2D;
    angle: number;
}

export class LocationTraitBuilder {
}

export class MetaTrait implements Trait {
    id: number;
}

export class MetaTraitBuilder {
}

export class ModelTrait implements Trait {
    vertexPointList: Point2D[];
    angleOffset: number;
    furtherPoint: number;
    vertexColor: RGBA;
    polygonColor: RGBA;
}

export class ModelTraitBuilder {
}

export class OwnerTrait implements Trait {
    ownerId: string;
}

export class OwnerTraitBuilder {
}

export class RenderTrait implements Trait {
    modelPointList: Point2D[];
    vertexColor: RGBA;
    polygonColor: RGBA;
}

export class RenderTraitBuilder {
}

export interface Trait {
}

export interface Command {
}

export class Point2D {
    x: number;
    y: number;
}
