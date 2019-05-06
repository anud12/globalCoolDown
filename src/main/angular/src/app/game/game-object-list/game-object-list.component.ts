import {Component, OnInit} from '@angular/core';
import {GameObject} from "../../models/GameObject";
import {WorldService} from "../world.service";
import {MetaTrait} from "../../java.models";
import {GameObjectService} from "../game-object.service";

@Component({
    selector: 'app-game-object-list',
    templateUrl: './game-object-list.component.html',
    styleUrls: ['./game-object-list.component.scss']
})
export class GameObjectListComponent implements OnInit {

    gameObjectMap: Map<number, GameObject> = new Map();
    gameObjectId: Array<number> = []

    constructor(private worldService: WorldService,
                private gameObjectService: GameObjectService) {
    }

    ngOnInit(): void {
        this.gameObjectService.personalGameObjectIdListSubject.subscribe(value => {
            this.gameObjectId = value;
        })
        this.gameObjectService.personalGameObjectByIdSubject.subscribe(value => {
            this.gameObjectMap = value;
        })
    }

    replaceInMap(gameObjectModel: GameObject, tempMap: Map<number, GameObject>) {
        const key = (gameObjectModel.traitMap.MetaTrait as MetaTrait).id;
        tempMap.set(key, gameObjectModel);
        const secondValue = this.gameObjectMap.get(key);
        if (secondValue) {
            tempMap.get(key).client = secondValue.client
        }
    }
}
