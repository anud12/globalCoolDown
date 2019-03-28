/* tslint:disable */
// Generated using typescript-generator version 2.12.476 on 2019-03-28 11:45:27.

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

export interface Trait {
}

export interface Command {
}

export class Point2D {
    x: number;
    y: number;
}
