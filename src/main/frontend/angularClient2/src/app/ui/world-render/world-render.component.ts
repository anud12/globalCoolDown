import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {PawnService} from "../../pawn/pawn.service";
import {UiService} from "../ui.service";
import {AuthenticationService} from "../../authentication/authentication.service";

@Component({
    selector: 'app-world-render',
    templateUrl: './world-render.component.html',
    styleUrls: ['./world-render.component.css']
})
export class WorldRenderComponent implements OnInit {

    private strokeColor: string;
    private ownColor: string;
    private neutralColor: string;
    private backgroundColor: string;

    constructor(private pawnService: PawnService,
                private uiService: UiService,
                @Inject('Window') private window: Window,
                private authenticationService: AuthenticationService) {
        this.strokeColor = "white";
        this.ownColor = "green";
        this.neutralColor = "teal";
        this.backgroundColor = "#424242"
    }

    @ViewChild('canvas') canvasRef: ElementRef;
    private canvas: HTMLCanvasElement;
    public width: number = 0;
    public height: number = 0;

    ngOnInit() {
        this.uiService.getDrawObserver().subscribe(() => {
            this.draw();
        });
        this.canvas = this.canvasRef.nativeElement;
        this.onResize();
    }

    draw() {
        if (this.canvas.getContext) {
            const context = this.canvas.getContext('2d');
            //fill in the background
            context.fillStyle = this.backgroundColor;
            context.fillRect(0, 0, this.width, this.height);

            this.pawnService.getListById().forEach(value => {
                context.font = "25px gnu-unifont";
                if (this.authenticationService.getModel().id === value.userId) {
                    context.fillStyle = this.ownColor;
                } else {
                    context.fillStyle = this.neutralColor;
                }

                context.fillText(
                    String.fromCharCode(value.characterCode) as any,
                    value.point.x * this.uiService.coordinateScale,
                    value.point.y * this.uiService.coordinateScale
                );
            });

        }
    }

    onResize() {
        this.height = this.window.innerHeight;
        this.width = this.window.innerWidth;
        this.canvas.height = this.height;
        this.canvas.width = this.width;
        this.draw();
    }
}
