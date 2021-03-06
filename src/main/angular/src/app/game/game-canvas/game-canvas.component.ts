import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {StompService} from "../../stomp.service";
import {GameObjectModel} from "../../java.models";
import {GlService} from "../../opengl/gl.service";
import {GameObjectService} from "../game-object.service";
import {GameInputService} from "../game-input.service";
import {GameSettingsService} from "../game-settings.service";
import {HttpClient} from "@angular/common/http";


@Component({
    selector: 'app-game-canvas',
    templateUrl: './game-canvas.component.html',
    styleUrls: ['./game-canvas.component.scss']
})
export class GameCanvasComponent implements AfterViewInit {
    @ViewChild('glcanvas') glcanvas: ElementRef;
    @ViewChild('parentDiv') parentDiv: ElementRef;
    private camera = {
        x: 0,
        y: 0,
        scale: 1
    }
    private gameObjectList = [];
    private glService;

    constructor(private stompService: StompService,
                private gameObjectService: GameObjectService,
                private gameInputService: GameInputService,
                private gameSettingsService: GameSettingsService,
                private httpClient: HttpClient) {

    }

    ngAfterViewInit(): void {
        this.glService = new GlService(this.glcanvas.nativeElement, this.httpClient, this.gameSettingsService, );

        this.stompService.subscribeGlobal<Array<GameObjectModel>>("/ws/world/all", gameObjectList => {
            this.gameObjectList = gameObjectList;
        })
        const drawCallback = () => {
            this.draw()
            requestAnimationFrame(drawCallback)
        };
        requestAnimationFrame(drawCallback);
        this.glcanvas.nativeElement.addEventListener("wheel", (event: WheelEvent) => {
            this.camera.scale += event.deltaY / 100;
            if (this.camera.scale < 0.10) {
                this.camera.scale = 0.10;
            }
        })

        this.glcanvas.nativeElement.addEventListener("contextmenu", event => {
            event.preventDefault()
            const point = {
                x: (event.offsetX / this.camera.scale) - this.camera.x,
                y: (event.offsetY / this.camera.scale) - this.camera.y
            }
            this.gameInputService.mouseRightClick(point, {
                shiftKey: event.shiftKey,
                ctrlKey: event.ctrlKey,
                altKey: event.altKey
            });
        });
        this.glcanvas.nativeElement.addEventListener("click", (event: MouseEvent) => {
            event.preventDefault()
            const point = {
                x: (event.offsetX / this.camera.scale) - this.camera.x,
                y: (event.offsetY / this.camera.scale) - this.camera.y
            }
            this.gameInputService.mouseLeftClick(point, {
                shiftKey: event.shiftKey,
                ctrlKey: event.ctrlKey,
                altKey: event.altKey
            });
        })
        this.glcanvas.nativeElement.addEventListener("mousemove", (event: MouseEvent) => {
            if (event.buttons === 1) {
                this.onLeftClickDrag(event)
            }
            if (event.buttons === 4) {
                this.onRightClickDrag(event)
            }
        })
    }

    draw() {
        this.glService.clear();
        this.gameObjectService.allGameObjectList.forEach(value => {
            this.glService.drawModel(value, this.camera);
        });
        this.gameObjectService.doForSelected(gameObject => {
            this.glService.drawSelection(gameObject, this.camera)
        })
    }

    onRightClickDrag(event: MouseEvent) {
        this.camera.x += event.movementX / this.camera.scale;
        this.camera.y += event.movementY / this.camera.scale;
    }

    onLeftClickDrag(event: MouseEvent) {

    }
}
