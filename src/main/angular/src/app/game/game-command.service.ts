import {Injectable} from '@angular/core';
import {ActionsWsEndpoints} from "../endpoints/action.ws.endpoints";
import {GameObjectService} from "./game-object.service";
import {StompService} from "../stomp.service";
import {Point} from "../java.models";

@Injectable({
    providedIn: 'root'
})
export class GameCommandService {
    
    constructor(private gameObjectService: GameObjectService,
                private stompService: StompService) {

    }

    sendTeleport(key: string | number, point: Point) {
        const url = ActionsWsEndpoints.teleport(key);
        console.log(url);
        this.stompService.publish(url, JSON.stringify(point));
    }


    sendMove(key: string | number, point: Point) {
        const url = ActionsWsEndpoints.move(key);
        console.log(url);
        this.stompService.publish(url, JSON.stringify(point));
    }

    sendCreate(key: string | number) {
        const url = ActionsWsEndpoints.create(key);
        console.log(url);
        this.stompService.publish(url, "");
    }
}
