import {Component, Input, OnInit} from '@angular/core';
import {GameObjectModel} from "../java.models";

@Component({
  selector: 'app-game-object',
  templateUrl: './game-object.component.html',
  styleUrls: ['./game-object.component.scss']
})
export class GameObjectComponent implements OnInit {

  @Input() gameObject: GameObjectModel;

  constructor() {
  }

  ngOnInit() {
  }

}
