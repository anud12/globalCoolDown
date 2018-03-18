import {PointModel} from "../../model/point.model";

export class PawnModel {
  constructor(public id: number,
              public value: number,
              public point: PointModel,
              public speed: number,
              public characterCode: number) {
  }
}
