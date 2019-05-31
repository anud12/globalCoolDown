/* tslint:disable */
// Generated using typescript-generator version 2.12.476 on 2019-05-31 17:03:18.

export class GameObjectMacro {
    definition: string;
    overwriteTraitMap: { [index: string]: Trait };
}

export class GameObjectMacroBuilder {
}

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

export class AgilityTrait implements Trait {
    rotationRate: number;
    translationRate: number;
}

export class CommandTrait implements Trait {
    plannerList: Command[];
}

export class LocationTrait implements Trait {
    point2D: Point2D;
    angle: number;
}

export class LocationTraitBuilder {
}

export class ManufacturingTrait implements Trait {
    canCreate: boolean;
}

export class ManufacturingTraitBuilder {
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
    modelRadius: number;
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
