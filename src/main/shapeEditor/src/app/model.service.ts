import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

export interface Point {
  x: number,
  y: number
}

@Injectable({
  providedIn: 'root'
})
export class ModelService {


  pointList: Array<Point> = [];
  pointListSubject = new BehaviorSubject(this.pointList);

  constructor() {
  }

  onPointListChange() {
    return this.pointListSubject.asObservable();
  }

  getAllPoints() {
    return this.pointList;
  }

  addPoint(point: Point) {
    this.pointList.push(point)
    this.pointListSubject.next(this.pointList)
  }

  removePoint(point: Point) {
    this.pointList = this.pointList.filter(value => {
      return !(
        (value.x === point.x)
        && (value.y === point.y)
      )
    })
    this.pointListSubject.next(this.pointList)
  }
}
