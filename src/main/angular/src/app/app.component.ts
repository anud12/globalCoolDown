import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {RxStompService} from "@stomp/ng2-stompjs";
import {GlService} from "./opengl/gl.service";
import {GameObjectModel, LocationTrait} from "./java.models";
import {StompService} from "./stomp.service";
import {Subject} from "rxjs";
import {ActionComponentEvent} from "./game-components/action/action.component";
import {GameObject} from "./models/GameObject";

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
    gameObjectId: Array<GameObject> = [];

    constructor(private rxStompService: RxStompService,
                private stompService: StompService) {
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
            // this.gameObjectMap.map(gameObject => {
            //     return gameObject.traitMap.MetaTrait.id;
            // })
            //     .forEach(id => {
            //         const url = `/ws/gameObject/${id}/${actionComponentEvent.endpoint}`;
            //         console.log(url);
            //         this.stompService.publish(url, actionComponentEvent.body);
            //     })
        });
        return actionSubject;
    }

    ngAfterViewInit(): void {
        const canvas = this.glcanvas.nativeElement;
        const glService = new GlService(canvas);

        this.stompService.subscribe<string>('/ws/hello', response => {
            this.message = response;
        });

        this.stompService.subscribe<Array<GameObjectModel>>("/ws/world", gameObjectList => {
            const tempMap = new Map();
            this.gameObjectId = [];
            gameObjectList.forEach((value: GameObject) => {
                value.client = {};
                this.replaceInMap(value, tempMap);
                this.gameObjectId.push(value.traitMap.MetaTrait.id);
            });
            this.gameObjectMap = tempMap;

            glService.clear();
            glService.draw(gameObjectList.map(value1 => {
                const trait = value1.traitMap.LocationTrait as LocationTrait;
                return trait.point2D
            }));
        })
    }

    replaceInMap(gameObjectModel: GameObject, tempMap: Map<GameObject>) {
        const key = gameObjectModel.traitMap.MetaTrait.id;
        tempMap.set(key, gameObjectModel);
        const secondValue = this.gameObjectMap.get(key);
        if (secondValue) {
            tempMap.get(key).client = secondValue.client
        }
    }

}

