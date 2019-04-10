/* tslint:disable */
// Generated using typescript-generator version 2.12.476 on 2019-04-10 16:48:44.

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
}

export class LocationTraitBuilder {
}

export class MetaTrait implements Trait {
    id: number;
}

export class MetaTraitBuilder {
}

export class OwnerTrait implements Trait {
    ownerId: string;
}

export class OwnerTraitBuilder {
}

export class RenderTrait implements Trait {
    modelPointList: Point2D[];
    color: Color;
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

export class Paint {
    opaque: boolean;
}

export class Color extends Paint implements Interpolatable<Color> {
    red: number;
    green: number;
    blue: number;
    opacity: number;
    hue: number;
    saturation: number;
    brightness: number;
}

export interface Interpolatable<T> {
}
