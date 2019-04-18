import {Injectable} from '@angular/core';
import {StompService} from "../stomp.service";
import {GameObject} from "../models/GameObject";
import {GameObjectModel, MetaTrait} from "../java.models";
import {WorldWsEnpoints} from "../endpoints/world.ws.endpoints";
import {SecurityService} from "../security/security.service";
import {Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class GameObjectService {


    personalGameObjectById: Map<number, GameObject> = new Map();
    personalGameObjectByIdSubject = new Subject<Map<number, GameObject>>();
    personalGameObjectIdList: Array<number> = [];
    personalGameObjectIdListSubject = new Subject<Array<number>>();
    allGameObjectList: Array<GameObject> = [];

    constructor(private stompService: StompService,
                private securityService: SecurityService) {
        this.securityService.onTokenChange().subscribe(token => {
            this.stompService.subscribePersonal<Array<GameObjectModel>>(WorldWsEnpoints.personal, token, gameObjectList => {
                const tempMap = new Map();
                this.personalGameObjectIdList = [];
                gameObjectList.forEach((value: GameObject) => {
                    value.client = {};
                    this.replaceInMap(value, tempMap);
                    this.personalGameObjectIdList.push((value.traitMap.MetaTrait as MetaTrait).id);
                });
                this.personalGameObjectById = tempMap;
                this.personalGameObjectIdListSubject.next(this.personalGameObjectIdList);
                this.personalGameObjectByIdSubject.next(this.personalGameObjectById)
            })
        });
        this.stompService.subscribeGlobal<Array<GameObjectModel>>(WorldWsEnpoints.all, response => {
            this.allGameObjectList = response as Array<GameObject>;
        })
    }


    replaceInMap(gameObjectModel: GameObject, tempMap: Map<number, GameObject>) {
        const key = (gameObjectModel.traitMap.MetaTrait as MetaTrait).id;
        tempMap.set(key, gameObjectModel);
        const secondValue = this.personalGameObjectById.get(key);
        if (secondValue) {
            tempMap.get(key).client = secondValue.client
        }
    }

    doForSelected(func: (gameObject: GameObjectModel, id: number) => void) {
        this.personalGameObjectById.forEach((value, key) => {
            if (value.client.selected) {
                func(value, key)
            }
        })

    }
}
