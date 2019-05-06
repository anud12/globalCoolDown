import {Injectable} from '@angular/core';
import {Point} from "../java.models";
import {GameObjectService} from "./game-object.service";
import {GameCommandService} from "./game-command.service";
import {StompService} from "../stomp.service";

export interface ModifierKeys {
    shiftKey: boolean,
    ctrlKey: boolean,
    altKey: boolean
}

@Injectable({
    providedIn: 'root'
})
export class GameInputService {

    rightClickCommand: (id, point) => void = this.gameCommandService.sendMove;
    private stompService: StompService; // required
    constructor(private gameObjectService: GameObjectService,
                private gameCommandService: GameCommandService,
                stompService: StompService) {
        this.stompService = stompService;
    }

    mouseRightClick(point: Point, modifierKeys: ModifierKeys) {
        this.gameObjectService.doForSelected((gameObject, id) => {
            this.rightClickCommand(id, point)
        })

    }

    mouseLeftClick(point: Point, modifierKeys: ModifierKeys) {
        if (!modifierKeys.shiftKey) {
            this.gameObjectService.clearSelection();
        }
        this.gameObjectService.selectAllAt(point);
    }

}
