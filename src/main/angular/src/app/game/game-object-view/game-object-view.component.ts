import {Component, Input, OnInit} from '@angular/core';
import {GameObject} from "../../models/GameObject";

@Component({
    selector: 'app-game-object-view',
    templateUrl: './game-object-view.component.html',
    styleUrls: ['./game-object-view.component.scss']
})
export class GameObjectViewComponent implements OnInit {
    @Input() gameObject: GameObject;

    constructor() {
    }

    ngOnInit() {
    }

    onClick() {
        this.gameObject.client.selected = !this.gameObject.client.selected
    }
}
