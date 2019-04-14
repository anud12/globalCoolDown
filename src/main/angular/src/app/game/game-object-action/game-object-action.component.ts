import {Component, OnInit} from '@angular/core';
import {Point} from "../../java.models";
import {ActionsWsEndpoints} from "../../endpoints/action.ws.endpoints";
import {GameObjectService} from "../game-object.service";
import {StompService} from "../../stomp.service";

@Component({
    selector: 'app-game-object-action',
    templateUrl: './game-object-action.component.html',
    styleUrls: ['./game-object-action.component.scss']
})
export class GameObjectActionComponent implements OnInit {

    teleport: Point = {
        x: 0,
        y: 0
    };
    move: Point = {
        x: 0,
        y: 0
    };

    constructor(private gameObjectService: GameObjectService,
                private stompService: StompService) {

    }

    ngOnInit() {
    }

    sendTeleport() {
        this.gameObjectService.personalGameObjectById.forEach((value, key) => {
            if (value.client.selected) {
                const url = ActionsWsEndpoints.teleport(key);
                console.log(url);
                this.stompService.publish(url, JSON.stringify(this.teleport));
            }
        })
    }


    sendMove() {
        this.gameObjectService.personalGameObjectById.forEach((value, key) => {
            if (value.client.selected) {
                const url = ActionsWsEndpoints.move(key);
                console.log(url);
                this.stompService.publish(url, JSON.stringify(this.move));
            }
        })
    }

    sendCreate() {

        this.gameObjectService.personalGameObjectById.forEach((value, key) => {
            if (value.client.selected) {
                const url = ActionsWsEndpoints.create(key);
                console.log(url);
                this.stompService.publish(url, "");
            }
        })
    }

}
