import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {RxStompService} from "@stomp/ng2-stompjs";
import {GlService} from "./opengl/gl.service";
import {GameObjectModel, LocationTrait, MetaTrait} from "./java.models";
import {StompService} from "./stomp.service";
import {Subject} from "rxjs";
import {ActionComponentEvent} from "./game-components/action/action.component";
import {GameObject} from "./models/GameObject";
import {SecurityService} from "./security/security.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {
    message: string = "message";
    json = JSON
    @ViewChild('glcanvas') glcanvas: ElementRef;

    gameObjectMap: Map<number, GameObject> = new Map();
    gameObjectId: Array<number> = [];

    constructor(private rxStompService: RxStompService,
                private stompService: StompService,
                private securityService: SecurityService) {
    }

    getActionSubject() {
        const actionSubject = new Subject<ActionComponentEvent>();
        actionSubject.subscribe(actionComponentEvent => {
            this.gameObjectMap.forEach((value, key) => {
                if (value.client.selected) {
                    const url = `/ws/gameObject/${key}/${actionComponentEvent.endpoint}`;
                    console.log(key);
                    this.stompService.publish(url, actionComponentEvent.body);
                }
            })
        });
        return actionSubject;
    }

    ngAfterViewInit(): void {
        const canvas = this.glcanvas.nativeElement;
        const glService = new GlService(canvas);

        this.stompService.subscribe<string>('/ws/hello', response => {
            this.message = response;
        });

        this.securityService.onTokenChange().subscribe(value => {
            console.log("Subscribing to" + "/ws/world-" + value)
            this.stompService.subscribe<Array<GameObjectModel>>("/ws/world-" + value, gameObjectList => {
                const tempMap = new Map();
                this.gameObjectId = [];
                gameObjectList.forEach((value: GameObject) => {
                    value.client = {};
                    this.replaceInMap(value, tempMap);
                    this.gameObjectId.push((value.traitMap.MetaTrait as MetaTrait).id);
                });
                this.gameObjectMap = tempMap;
            })
        })

        this.stompService.subscribe<Array<GameObjectModel>>("/ws/world/all", gameObjectList => {
            glService.clear();
            glService.draw(gameObjectList.map(value1 => {
                const trait = value1.traitMap.LocationTrait as LocationTrait;
                return trait.point2D
            }));
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

