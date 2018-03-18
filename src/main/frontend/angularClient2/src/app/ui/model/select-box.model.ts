import {PointModel} from "../../model/point.model";

export class SelectBoxModel {
  constructor(public isSelected: boolean,
              public start: PointModel,
              public end: PointModel) {
  }
}
