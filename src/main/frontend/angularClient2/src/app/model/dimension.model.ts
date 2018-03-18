import {PointModel} from "./point.model";

export class DimensionModel {

  public start: PointModel;
  public end: PointModel;

  constructor() {
    this.start = new PointModel(0,0);
    this.end = new PointModel(0,0);
  }
}
