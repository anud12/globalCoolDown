import {Injectable} from '@angular/core';
import {Point} from "../java.models";
import {GameObjectService} from "./game-object.service";
import {GameCommandService} from "./game-command.service";
import {StompService} from "../stomp.service";

@Injectable({
    providedIn: 'root'
})
export class GameInputService {

    rightClickCommand: (id, point) => void = this.gameCommandService.sendMove;

    constructor(private gameObjectService: GameObjectService,
                private gameCommandService: GameCommandService,
                private stompService: StompService) {
    }

    mouseRightClick(point: Point) {
        this.gameObjectService.doForSelected((gameObject, id) => {
            this.rightClickCommand(id, point)
        })

    }

    mouseLeftClick(point: Point) {

    }

}
