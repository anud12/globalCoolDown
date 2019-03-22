/* tslint:disable */
// Generated using typescript-generator version 2.12.476 on 2019-03-22 12:47:41.

export class GameObjectModel {
    aspects: { [index: string]: Trait };
}

export class GameObjectModelBuilder {
}

export class CommandTrait implements Trait {
    commandList: Command[];
}

export class LocationTrait implements Trait {
    x: number;
    y: number;
}

export class LocationTraitBuilder {
}

export class MetaTrait implements Trait {
    name: string;
    id: number;
}

export class MetaTraitBuilder {
}

export interface Trait {
}

export interface Command {
}
