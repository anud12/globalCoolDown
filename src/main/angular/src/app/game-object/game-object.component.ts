import {Component, Input, OnInit} from '@angular/core';
import {GameObject} from "../models/GameObject";

@Component({
    selector: 'app-game-object',
    templateUrl: './game-object.component.html',
    styleUrls: ['./game-object.component.scss']
})
export class GameObjectComponent implements OnInit {

    @Input() gameObject: GameObject;

    constructor() {
    }

    ngOnInit() {
    }

    onClick() {
        this.gameObject.client.selected = !this.gameObject.client.selected
    }
}
