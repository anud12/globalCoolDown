import {Component, Input, OnInit} from '@angular/core';
import {GameObject} from "../../models/GameObject";
import {CommandTrait, LocationTrait, MetaTrait} from "../../java.models";

@Component({
    selector: 'app-game-object-view',
    templateUrl: './game-object-view.component.html',
    styleUrls: ['./game-object-view.component.scss']
})
export class GameObjectViewComponent implements OnInit {
    @Input() gameObject: GameObject;


    metaTrait: () => MetaTrait = () => this.gameObject.traitMap.MetaTrait as MetaTrait;
    locationTrait: () => LocationTrait = () => this.gameObject.traitMap.LocationTrait as LocationTrait;

    constructor() {
    }


    ngOnInit() {
    }

    onClick() {
        this.gameObject.client.selected = !this.gameObject.client.selected
    }
}
