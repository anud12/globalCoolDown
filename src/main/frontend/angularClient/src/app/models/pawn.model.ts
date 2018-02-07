import {PointModel} from "./point.model";

export class Pawn {
  constructor(public id: number,
              public name: string,
              public value: string,
              public version: string,
              public point:PointModel,
              public speed: number
              ) {
  }
}
