import {Injectable} from '@angular/core';
import {StompService} from "../stomp.service";
import {SecurityService} from "../security/security.service";
import {GameObjectModel} from "../java.models";
import {WorldWsEnpoints} from "../endpoints/world.ws.endpoints";

@Injectable({
    providedIn: 'root'
})
export class WorldService {

    constructor(private stompService: StompService,
                private securityService: SecurityService) {
    }

    getPersonal(callback: (gameObjectList: Array<GameObjectModel>) => void) {
        this.securityService.onTokenChange().subscribe(token => {
            this.stompService.subscribePersonal<Array<GameObjectModel>>(WorldWsEnpoints.personal, token, callback)
        })
    }

    getAll(callback: (gameObjectList: Array<GameObjectModel>) => void) {
        this.stompService.subscribeGlobal<Array<GameObjectModel>>(WorldWsEnpoints.all, callback)
    }
}
