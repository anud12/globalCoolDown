import {Component, OnInit} from '@angular/core';
import {GameObjectService} from "../game-object.service";
import {GameCommandService} from "../game-command.service";
import {GameInputService} from "../game-input.service";

@Component({
    selector: 'app-game-object-action',
    templateUrl: './game-object-action.component.html',
    styleUrls: ['./game-object-action.component.scss']
})
export class GameObjectActionComponent implements OnInit {
    constructor(private gameObjectService: GameObjectService,
                private gameInputService: GameInputService,
                private gameCommandService: GameCommandService) {

    }

    ngOnInit() {
    }

    setTeleport() {
        this.gameInputService.rightClickCommand = this.gameCommandService.sendTeleport;
    }


    setMove() {
        this.gameInputService.rightClickCommand = this.gameCommandService.sendMove;
    }

    sendCreate() {
        this.gameObjectService.doForSelected((gameObject, id) => {
            this.gameCommandService.sendCreate(id)
        });
    }

}
