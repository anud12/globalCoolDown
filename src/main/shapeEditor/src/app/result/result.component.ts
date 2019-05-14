import {Component, OnInit} from '@angular/core';
import {ModelService, Point} from "../model.service";

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss']
})
export class ResultComponent implements OnInit {

  pointList: Array<Point> = []

  constructor(private modelService: ModelService) {
  }

  ngOnInit() {
    this.modelService.onPointListChange().subscribe(value => {
      this.pointList = value
    })
  }

  getString() {
    let max = 0;
    this.pointList.forEach(value => {
      max = Math.max(max, Math.max(Math.abs(value.x), Math.abs(value.y)));
    })

    return JSON.stringify({
      "ro.anud.globalCooldown.data.trait.ModelTrait": {
        "class": "ro.anud.globalCooldown.data.trait.ModelTrait",
        "angleOffset": 90,
        "furtherPoint": max,
        "vertexPointList": this.pointList
      }
    }, null, 4)
  }


}
