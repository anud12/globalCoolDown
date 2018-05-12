import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {PawnService} from '../../pawn/pawn.service';
import {UiService} from '../ui.service';
import {AuthenticationService} from '../../authentication/authentication.service';
import {MapService} from '../../map/map.service';

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
    private mapBackgroundColor: string;
    private mapStrokeColor: string;

    constructor(private pawnService: PawnService,
                private areaService: MapService,
                private uiService: UiService,
                @Inject('Window') private window: Window,
                private authenticationService: AuthenticationService) {
        this.strokeColor = 'white';
        this.ownColor = 'green';
        this.neutralColor = 'teal';
        this.backgroundColor = 'black';
        this.mapBackgroundColor = '#263238';
        this.mapStrokeColor = '#78909C';
    }

    @ViewChild('canvas') canvasRef: ElementRef;
    private canvas: HTMLCanvasElement;
    public width: number = 0;
    public height: number = 0;

    ngOnInit() {
        this.areaService.getAreaStompSubscription().subscribe(() => {
            console.log('render area subscription');
        });
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
            this.drawArea(context);
            this.pawnService.getListById().forEach(value => {
                context.font = `${this.uiService.fontSize}px gnu-unifont`;
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

    drawArea(context: CanvasRenderingContext2D) {
        const area = this.areaService.getArea();
        context.beginPath();
        area.lineList.forEach(value => {
            context.lineTo(value.start.x * this.uiService.coordinateScale, value.start.y * this.uiService.coordinateScale);
        });
        context.closePath();
        context.fillStyle = this.mapBackgroundColor;
        context.fill();

        context.beginPath();
        area.lineList.forEach(value => {
            context.lineTo(value.start.x * this.uiService.coordinateScale, value.start.y * this.uiService.coordinateScale);
        });
       context.closePath();
       context.strokeStyle = this.mapStrokeColor;
       context.stroke();
    }
}
