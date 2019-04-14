import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {GlService} from "./opengl/gl.service";
import {GameObjectModel} from "./java.models";
import {StompService} from "./stomp.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {
    message: string = "message";
    json = JSON
    @ViewChild('glcanvas') glcanvas: ElementRef;

    constructor(private stompService: StompService) {
    }

    ngAfterViewInit(): void {
        const canvas = this.glcanvas.nativeElement;
        const glService = new GlService(canvas);

        this.stompService.subscribeGlobal<string>('/ws/hello', response => {
            this.message = response;
        });
        this.stompService.subscribeGlobal<Array<GameObjectModel>>("/ws/world/all", gameObjectList => {
            glService.clear();
            if (gameObjectList.length != 0) {
                glService.draw(gameObjectList);
            }
        })
    }


}

