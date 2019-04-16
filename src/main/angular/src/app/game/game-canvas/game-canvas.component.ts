import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {StompService} from "../../stomp.service";
import {GameObjectModel} from "../../java.models";
import {GlService} from "../../opengl/gl.service";

@Component({
    selector: 'app-game-canvas',
    templateUrl: './game-canvas.component.html',
    styleUrls: ['./game-canvas.component.scss']
})
export class GameCanvasComponent implements AfterViewInit {
    @ViewChild('glcanvas') glcanvas: ElementRef;
    @ViewChild('parentDiv') parentDiv: ElementRef;

    constructor(private stompService: StompService) {
    }

    ngAfterViewInit(): void {
        const canvas = this.glcanvas.nativeElement;
        const glService = new GlService(canvas);

        this.stompService.subscribeGlobal<Array<GameObjectModel>>("/ws/world/all", gameObjectList => {
            glService.clear();
            if (gameObjectList.length != 0) {
                glService.draw(gameObjectList);
            }
        })
    }

    onParentResize() {
        console.log("hi")
        const canvas: HTMLCanvasElement = this.glcanvas.nativeElement;
        const parent: HTMLElement = this.parentDiv.nativeElement;
        canvas.height = parent.clientHeight;
        canvas.width = parent.clientWidth;
    }
}
