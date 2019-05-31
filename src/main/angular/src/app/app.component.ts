import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
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
        this.stompService.subscribeGlobal<string>('/ws/hello', response => {
            this.message = response;
        });
    }


}

