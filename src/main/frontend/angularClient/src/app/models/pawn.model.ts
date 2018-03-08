import {PointModel} from "./point.model";

export class Pawn {
  public character: string;

  constructor(public id: number,
              public name: string,
              public value: string,
              public version: string,
              public point: PointModel,
              public speed: number,
              public characterCode: number) {
  }
}
